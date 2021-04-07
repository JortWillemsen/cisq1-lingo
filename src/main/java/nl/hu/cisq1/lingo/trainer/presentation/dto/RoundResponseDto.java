package nl.hu.cisq1.lingo.trainer.presentation.dto;

import nl.hu.cisq1.lingo.trainer.domain.Attempt;

import java.util.List;

public class RoundResponseDto {
    private Long gameId;
    private final int tries;
    private final boolean guessed;
    private final String currentHint;
    private final String wordToGuess;
    private final List<Attempt> attempts;

    public RoundResponseDto(Long id, int tries, boolean guessed, String hint, String wordToGuess, List<Attempt> attempts) {
        this.gameId = id;
        this.tries = tries;
        this.guessed = guessed;
        this.currentHint = hint;
        this.wordToGuess = wordToGuess;
        this.attempts = attempts;
    }

    public Long getGameId() {
        return gameId;
    }

    public int getTries() {
        return tries;
    }

    public boolean isGuessed() {
        return guessed;
    }

    public String getCurrentHint() {
        return currentHint;
    }

    public String getWordToGuess() {
        return wordToGuess;
    }

    public List<Attempt> getAttempts() {
        return attempts;
    }
}
