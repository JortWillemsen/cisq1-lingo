package nl.hu.cisq1.lingo.trainer.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoundTest {
    private Game game;
    private String word = "teste";

    @BeforeEach
    void initialize() {
        this.game = new Game();
        this.game.beginGame(this.word);
    }

    @Test
    void testRoundShouldNotBeNullWhenBeginningIt() {
        this.game.makeAttempt(word);

        Round round = this.game.nextRound("worde");
        assertNotNull(round);
    }

    @Test
    void testShouldThrowErrorWhenRoundIsAlreadyOngoing() {
        assertThrows(IllegalStateException.class, () -> this.game.nextRound("worde"));
    }

    @Test
    @DisplayName("Creating a round should create a hint object")
    void testCreateHint() {
        assertNotNull(this.game.getActiveRound().getHint());
    }

    @Test
    @DisplayName("Creating a round should set tries to 0")
    void testTriesAtBeginOfRound() {
        assertEquals(0, this.game.getActiveRound().getTries());
    }

    @Test
    @DisplayName("Creating a round should have no attempts")
    void testAttemptsAtBeginOfRound() {
        assertEquals(0, this.game.getActiveRound().getAttempts().size());
    }

    @Test
    void testAttempts() {
        this.game.makeAttempt("testo");
        assertEquals(1, this.game.getActiveRound().getAttempts().size());
    }

    @Test
    @DisplayName("Making an attempt should up the tries with 1")
    void testTriesAfterAttempt() {
        this.game.makeAttempt("testo");
        assertEquals(1, this.game.getActiveRound().getTries());
    }

    @Test
    @DisplayName("Creating a round should set the wordToGuess")
    void testWordToGuessAtBeginOfRound() {
        assertEquals(this.word, this.game.getActiveRound().getWordToGuess());
    }

}