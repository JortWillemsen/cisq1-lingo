package nl.hu.cisq1.lingo.trainer.domain;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "game")
public class Game {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column
    private int score;
    @Enumerated(EnumType.STRING)
    private GameStatus status;

    @OneToMany
    private List<Round> rounds = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    private Round activeRound;

    public Game() {
        this.score = 0;
        this.activeRound = null;
    }

    public Game beginGame(String startingWord) {
        this.rounds.clear();
        checkStatus();
        this.nextRound(startingWord);
        return this;
    }

    public Round getActiveRound() {
        return activeRound;
    }

    public GameStatus getStatus() {
        return status;
    }

    public int getScore() {
        return score;
    }

    public List<Round> getRounds() {
        return rounds;
    }

    public Round nextRound(String wordToGuess) {
        this.activeRound = new Round(wordToGuess);
        return this.activeRound.beginRound();
    }



    private void checkStatus() {
        if(this.rounds.isEmpty() && this.activeRound == null) {
            this.status = GameStatus.GAME_STARTING;
            return;
        }
        if(this.activeRound != null && this.activeRound.getTries() < 5) {
            this.status = GameStatus.GAME_PLAYING;
        }
        assert this.activeRound != null;
        if(this.activeRound.getTries() > 4) {
            this.status = GameStatus.GAME_ELIMINATED;
        }
        if(this.activeRound.getHint().getHint().equals(this.activeRound.getWordToGuess())) {
            this.status = GameStatus.ROUND_WON;
        }
    }

    private void endRound() {
        this.rounds.add(this.activeRound);
        calculateScore();
    }

    private void calculateScore() {
        checkStatus();
        if(this.status == GameStatus.ROUND_WON)
            this.score += (5*(5 - this.activeRound.getTries()) + 5);
    }

    public Attempt makeAttempt(String guess) {
        if(this.getActiveRound().getTries() > 4){
            this.endRound();
        }

        Attempt attempt = this.activeRound.makeAttempt(guess);
        if(attempt.correct()) {
            this.endRound();
        }
        checkStatus();
        return attempt;
    }

    public int provideLenghtOfNewWord() {
        return switch ( this.activeRound.getWordToGuess().length() ) {
            case 5 -> 6;
            case 6 -> 7;
            default -> 5;
        };
    }


}
