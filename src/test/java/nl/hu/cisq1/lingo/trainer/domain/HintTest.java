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
    private static final Game game = new Game();

    @BeforeAll
    static void initialize() {
        String word = "teste";
        game.beginGame(word);
    }

    @ParameterizedTest
    @MethodSource("provideHints")
    @DisplayName("Exceeding max attempts results in throwing an exception")
    void testHints(String guess, String expectedHint) {
        Attempt attempt = game.makeAttempt(guess);
        assertEquals(expectedHint, attempt.getHint());
    }

    static Stream<Arguments> provideHints() {
        return Stream.of(
                Arguments.of("00000", "t...."),
                Arguments.of("telol", "te..."),
                Arguments.of("tesle", "tes.e"),
                Arguments.of("tosto", "teste"),
                Arguments.of("teste", "teste")
        );
    }
}