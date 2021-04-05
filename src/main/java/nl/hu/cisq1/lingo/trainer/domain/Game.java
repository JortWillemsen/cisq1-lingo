package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.exception.IllegalStatusException;

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
    private boolean finished = false;

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

    public boolean isFinished() {
        return this.finished;
    }

    public Game beginGame(String startingWord) {
        this.rounds.clear();
        this.nextRound(startingWord);
        return this;
    }

    public Round nextRound(String wordToGuess) {
        if(this.status == GameStatus.GAME_PLAYING)
            throw new IllegalStatusException("Round is already ongoing");

        this.activeRound = new Round(wordToGuess);
        updateStatus();

        return this.activeRound.beginRound();
    }

    public GameStatus updateStatus() {
        this.status = null;
        if(this.finished)
        { this.status = GameStatus.GAME_ELIMINATED; return this.status; }
        if(this.rounds.isEmpty() && this.activeRound == null)
            this.status = GameStatus.GAME_STARTING;
        if(this.activeRound != null && this.activeRound.getTries() < 5)
            this.status = GameStatus.GAME_PLAYING;
        if(this.activeRound != null && this.activeRound.getTries() > 4)
            this.status = GameStatus.GAME_ELIMINATED;
        if(this.activeRound != null && this.activeRound.getHint().getHint().equals(this.activeRound.getWordToGuess()))
            this.status = GameStatus.ROUND_WON;
        return this.status;
    }

    private void endRound() {
        this.rounds.add(this.activeRound);
        calculateScore();
        if(updateStatus().equals(GameStatus.GAME_ELIMINATED))
            this.finishGame();
    }

    private void calculateScore() {
        if(updateStatus().equals(GameStatus.ROUND_WON))
            this.score += (5*(5 - this.activeRound.getTries()) + 5);
    }

    public Attempt makeAttempt(String guess) {
        if(!updateStatus().equals(GameStatus.GAME_PLAYING))
            throw new IllegalStatusException("You cannot make an attempt");

        Attempt attempt = this.activeRound.makeAttempt(guess);

        if(updateStatus().equals(GameStatus.ROUND_WON) || updateStatus().equals(GameStatus.GAME_ELIMINATED))
            this.endRound();

        return attempt;
    }

    public int provideLenghtOfNewWord() {
        return switch ( this.activeRound.getWordToGuess().length() ) {
            case 5 -> 6;
            case 6 -> 7;
            default -> 5;
        };
    }

    public void finishGame() {
        this.finished = true;
        updateStatus();
    }


    public Long getId() {
        return this.id;
    }
}
