package nl.hu.cisq1.lingo.trainer.presentation.dto;

import nl.hu.cisq1.lingo.trainer.domain.GameStatus;

public class GameResponseDto {
    public Long id;
    public int score;
    public int numberOfRounds;
    public RoundResponseDto activeRound;
    public GameStatus status;
    public boolean finished;

    public GameResponseDto(Long id, int score, int numberOfRounds, RoundResponseDto activeRound, GameStatus status, boolean finished) {
        this.id = id;
        this.score = score;
        this.numberOfRounds = numberOfRounds;
        this.activeRound = activeRound;
        this.status = status;
        this.finished = finished;
    }
}
