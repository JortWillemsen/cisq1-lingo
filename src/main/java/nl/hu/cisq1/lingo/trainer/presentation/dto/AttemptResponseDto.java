package nl.hu.cisq1.lingo.trainer.presentation.dto;

import nl.hu.cisq1.lingo.trainer.domain.Mark;

import java.util.List;

public class AttemptResponseDto {
    private final Long gameId;
    private final String hint;
    private final String guess;
    private final List<Mark> feedback;

    public AttemptResponseDto(Long id, String hint, String guess, List<Mark> feedback) {
        this.gameId = id;
        this.hint = hint;
        this.guess = guess;
        this.feedback = feedback;
    }

    public Long getGameId() {
        return gameId;
    }

    public String getHint() {
        return hint;
    }

    public String getGuess() {
        return guess;
    }

    public List<Mark> getFeedback() {
        return feedback;
    }
}
