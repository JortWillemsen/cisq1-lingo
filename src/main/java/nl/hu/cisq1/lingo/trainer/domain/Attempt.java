package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.words.domain.Word;

import java.util.List;

public class Attempt {
    private Word attempt;
    private Feedback feedback;

    public Attempt(Word attempt) {
        this.attempt = attempt;
    }

    public Feedback getFeedback() {
        return feedback.calculate();
    }
}
