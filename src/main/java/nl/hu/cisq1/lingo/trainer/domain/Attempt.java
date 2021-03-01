package nl.hu.cisq1.lingo.trainer.domain;

import java.util.List;

public class Attempt {
    private String attempt;
    private Feedback feedback;

    public Attempt(String attempt, String wordToGuess) {
        this.attempt = attempt;
        this.feedback = new Feedback(attempt, wordToGuess);
    }

    public List<Mark> getFeedback() {
        return feedback.calculate();
    }
}
