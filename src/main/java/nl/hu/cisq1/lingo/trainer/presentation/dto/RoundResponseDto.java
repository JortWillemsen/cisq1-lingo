package nl.hu.cisq1.lingo.trainer.presentation.dto;

import nl.hu.cisq1.lingo.trainer.domain.Attempt;

import java.util.List;

public class RoundResponseDto {
    public int tries;
    public boolean guessed;
    public String currentHint;
    public String wordToGuess;
    public List<Attempt> attempts;

    public RoundResponseDto(int tries, boolean guessed, String hint, String wordToGuess, List<Attempt> attempts) {
        this.tries = tries;
        this.guessed = guessed;
        this.currentHint = hint;
        this.wordToGuess = wordToGuess;
        this.attempts = attempts;
    }
}
