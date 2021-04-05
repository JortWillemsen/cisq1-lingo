package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.exception.InvalidAttemptException;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "attempt")
public class Attempt {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column
    private String guess;
    private String hint;

    @OneToOne(cascade = CascadeType.ALL)
    private Feedback feedback;


    public Attempt() { }
    public Attempt(String guess, String wordToGuess, String hint) {
        this.guess = guess;

        if (guess == null || guess.length() != wordToGuess.length()) {
            throw new InvalidAttemptException("Attempt is not valid.");
        }

        this.feedback = new Feedback(guess, wordToGuess);
        this.hint = hint;
    }

    public List<Mark> getFeedback() {
        return feedback.calculate();
    }

    public String getGuess() {
        return guess;
    }

    public String getHint() {
        return hint;
    }

    public boolean correct() {
        return this.feedback.isGuessed();
    }
}
