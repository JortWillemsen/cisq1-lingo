package nl.hu.cisq1.lingo.trainer.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class AttemptTest {
    private Game game;
    private String word = "teste";

    @BeforeEach
    void initialize() {
        this.game = new Game();
        this.game.beginGame(this.word);
    }

    @Test
    @DisplayName("Creating an attempt should set the guess.")
    void testSetGuessAtCreation() {
        Attempt attempt = this.game.makeAttempt("testo");
        assertNotNull(attempt.getGuess());
    }

    @Test
    @DisplayName("Creating an attempt should set the hint.")
    void testSetHintAtCreation() {
        Attempt attempt = this.game.makeAttempt("testo");
        assertNotNull(attempt.getHint());
    }

    @Test
    @DisplayName("The returned hint should be as long as the wordToGuess")
    void testLengthOfHint() {
        Attempt attempt = this.game.makeAttempt("testo");
        assertEquals(5, attempt.getHint().length());
    }

    @Test
    void testCorrectisTrueWhenWordsMatch() {
        Attempt attempt = this.game.makeAttempt(this.word);
        assertTrue(attempt.correct());
    }

    @Test
    void testCorrectIsFalseWhenWordsDoNotMatch() {
        Attempt attempt = this.game.makeAttempt("testo");
        assertFalse(attempt.correct());
    }

    @Test
    @DisplayName("The returned guess should be the same as given guess")
    void testReturnOfGetGuess() {
        Attempt attempt = this.game.makeAttempt("testo");
        assertEquals("testo", attempt.getGuess());
    }


    @ParameterizedTest
    @MethodSource("inputAttempts")
    @DisplayName("Attempt should return valid feedback")
    void testFeedbackForAttempts(String guess, List<Mark> feedback) {
        Attempt attempt = this.game.makeAttempt(guess);
        assertEquals(feedback, attempt.getFeedback());
    }

    static Stream<Arguments> inputAttempts() {
        return Stream.of(
                Arguments.of("testo", new ArrayList<>(Arrays.asList(Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.INCORRECT))),
                Arguments.of("toste", new ArrayList<>(Arrays.asList(Mark.CORRECT, Mark.INCORRECT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT))),
                Arguments.of("totos", new ArrayList<>(Arrays.asList(Mark.CORRECT, Mark.INCORRECT, Mark.PRESENT, Mark.INCORRECT, Mark.PRESENT))),
                Arguments.of("t.ste", new ArrayList<>(Arrays.asList(Mark.CORRECT, Mark.INVALID, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT)))
        );
    }
}