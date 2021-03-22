package nl.hu.cisq1.lingo.trainer.domain;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class HintTest {
    private Hint hint;
    @BeforeEach
    void initialize() {
        this.hint = new Hint("teste");
    }

    @ParameterizedTest
    @MethodSource("provideHints")
    @DisplayName("Test if the returned hint is calculated correctly")
    void testHints(String guess, String expectedHint) {
        assertEquals(expectedHint, hint.calculate(guess).getHint());
    }

    static Stream<Arguments> provideHints() {
        return Stream.of(
                Arguments.of("00000", "t...."),
                Arguments.of("telol", "te..."),
                Arguments.of("tesle", "tes.e"),
                Arguments.of("teste", "teste")
        );
    }
}