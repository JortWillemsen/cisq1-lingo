package nl.hu.cisq1.lingo.trainer.presentation.dto;

public class StartRoundResponseDto {
    private final String hint;

    public StartRoundResponseDto(String hint) {
        this.hint = hint;
    }

    public String getHint() {
        return hint;
    }
}
