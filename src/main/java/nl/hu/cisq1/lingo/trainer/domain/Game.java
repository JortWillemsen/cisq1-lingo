package nl.hu.cisq1.lingo.trainer.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "game")
public class Game {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column
    private int score;
    private boolean finished;
    private GameStatus status;

    @OneToMany
    private List<Round> rounds = new ArrayList<>();

    @OneToOne
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
        System.out.println("Beginning new round");

        this.activeRound = new Round(wordToGuess);
        return this.activeRound.beginRound();
    }



    private void checkStatus() {
        if(this.rounds.size() == 0 && this.activeRound == null) {
            this.status = GameStatus.GAME_STARTING;
            return;
        }
        if(this.activeRound != null && this.activeRound.getTries() < 5) {
            this.status = GameStatus.GAME_PLAYING;
        }
        if(this.activeRound.getTries() > 4) {
            this.status = GameStatus.GAME_ELIMINATED;
        }
        if(this.activeRound.getHint().getHint().equals(this.activeRound.getWordToGuess())) {
            this.status = GameStatus.ROUND_WON;
        }
    }

    private void endRound() {
        System.out.println("Ending round");
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
            this.finished = true;
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
