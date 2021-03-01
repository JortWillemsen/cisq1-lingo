package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.words.domain.Word;

import java.util.List;

public class Feedback {
    private Word guess;
    private List<Mark> feedback;

    public Feedback(Word guess) {
        this.guess = guess;
    }

    public Feedback calculate() {
        return this;
    }
}
