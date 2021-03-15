package nl.hu.cisq1.lingo.trainer.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "round")
public class Round {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column
    private String wordToGuess;

    @OneToMany
    private List<Attempt> attempts = new ArrayList<>();
    private int tries;

    @Transient
    private Hint hint;

    public Round() {}
    public Round(String wordToGuess) {
        this.wordToGuess = wordToGuess;
        this.hint = new Hint(wordToGuess);
        this.tries = 0;
    }

    public Attempt makeAttempt(String guess) {
        Attempt attempt = new Attempt(guess, wordToGuess, this.hint.calculate(guess).getHint());
        this.attempts.add(attempt);
        this.tries++;
        return attempt;
    }

    public List<Attempt> getAttempts() {
        return attempts;
    }

    public int getTries() {
        return tries;
    }

    public void setTries(int tries) {
        this.tries = tries;
    }

    public Hint getHint() {
        return hint;
    }

    public Round beginRound() {
        return this;
    }

    public String getWordToGuess() {
        return wordToGuess;
    }
}
