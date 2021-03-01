package nl.hu.cisq1.lingo.trainer.domain;

import java.util.ArrayList;
import java.util.List;

public class Round {
    private String wordToGuess;
    private List<Attempt> attempts = new ArrayList<>();

    public Round(String wordToGuess) {
        this.wordToGuess = wordToGuess;
    }

    public Attempt makeAttempt(String guess) {
        Attempt attempt = new Attempt(guess, wordToGuess);
        this.attempts.add(attempt);
        return attempt;
    }
}
