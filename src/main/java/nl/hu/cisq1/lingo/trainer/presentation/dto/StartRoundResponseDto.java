package nl.hu.cisq1.lingo.trainer.presentation.dto;

public class StartRoundResponseDto {
    private final Long id;
    private final String hint;

    public StartRoundResponseDto(Long id, String hint) {
        this.id = id;
        this.hint = hint;
    }

    public Long getId() {
        return id;
    }

    public String getHint() {
        return hint;
    }
}
