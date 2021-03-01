package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.words.domain.Word;

import java.util.List;

public class Round {
    private Word wordToGuess;
    private List<Attempt> attempts;

    public Round(Word wordToGuess) {
        this.wordToGuess = wordToGuess;
    }
}
