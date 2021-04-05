package nl.hu.cisq1.lingo.trainer.presentation.dto;

import nl.hu.cisq1.lingo.trainer.domain.GameStatus;

public class GameResponseDto {
    private final Long id;
    private final int score;
    private final int numberOfRounds;
    private final RoundResponseDto activeRound;
    private final GameStatus status;
    private final boolean finished;

    public GameResponseDto(Long id, int score, int numberOfRounds, RoundResponseDto activeRound, GameStatus status, boolean finished) {
        this.id = id;
        this.score = score;
        this.numberOfRounds = numberOfRounds;
        this.activeRound = activeRound;
        this.status = status;
        this.finished = finished;
    }

    public Long getId() {
        return id;
    }

    public int getScore() {
        return score;
    }

    public int getNumberOfRounds() {
        return numberOfRounds;
    }

    public RoundResponseDto getActiveRound() {
        return activeRound;
    }

    public GameStatus getStatus() {
        return status;
    }

    public boolean isFinished() {
        return finished;
    }
}
