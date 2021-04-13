package nl.hu.cisq1.lingo.trainer.presentation.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public class AttemptRequestDto {
    @NotBlank @NotEmpty
    public String guess;
}
