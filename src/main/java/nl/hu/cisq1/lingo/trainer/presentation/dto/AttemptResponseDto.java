package nl.hu.cisq1.lingo.trainer.presentation.dto;

import nl.hu.cisq1.lingo.trainer.domain.Mark;

import java.util.List;

public class AttemptResponseDto {
    public String hint;
    public String guess;
    public List<Mark> feedback;

    public AttemptResponseDto(String hint, String guess, List<Mark> feedback) {
        this.hint = hint;
        this.guess = guess;
        this.feedback = feedback;
    }
}
