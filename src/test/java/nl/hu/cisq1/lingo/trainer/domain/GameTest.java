package nl.hu.cisq1.lingo.trainer.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    private Game game;

    @BeforeEach
    void initialize() {
        this.game = new Game();
    }

    @Test
    @DisplayName("Starting the game should create a new round and set it to active")
    void testStartGame() {
        game.beginGame("hallos");
        assertEquals(new ArrayList<>(), game.getRounds());
        assertNotNull(game.getActiveRound());
    }

    @Test
    @DisplayName("Ending a round should set GameState to ROUND_WON when word is guesssed correctly")
    void testRoundWon() {
        game.beginGame("hollos");
        game.makeAttempt(game.getActiveRound().getWordToGuess());
        assertEquals(GameStatus.ROUND_WON, game.getStatus());
    }

    @Test
    @DisplayName("Not guessing the word correctly should not update the score")
    void testNotCalculateScore() {
        game.beginGame("hollos");
        game.makeAttempt("hoolos");
        game.makeAttempt("hoolos");
        game.makeAttempt("hoolos");
        game.makeAttempt("hoolos");
        game.makeAttempt("hoolos");
        game.makeAttempt("hoolos");
        assertEquals(0, game.getScore());
    }

    @ParameterizedTest
    @MethodSource("inputMaxAttempts")
    @DisplayName("Exceeding max attempts results in throwing an exception")
    void testMaxAttempts(int tries, String guess, GameStatus status) {
        this.game.beginGame("moeder");
        this.game.getActiveRound().setTries(tries);
        this.game.makeAttempt(guess);
        assertEquals(status, game.getStatus());
    }

    static Stream<Arguments> inputMaxAttempts() {
        return Stream.of(
                Arguments.of(1, "moeser", GameStatus.GAME_PLAYING),
                Arguments.of(1, "moeder", GameStatus.ROUND_WON),
                Arguments.of(4, "moeser", GameStatus.GAME_ELIMINATED)
        );
    }

    @Test
    @DisplayName("Beginning a game should clear the rounds list")
    void testGameShouldClearRoundsList() {
        this.game.nextRound("wordss");
        this.game.makeAttempt("wordss");
        this.game.beginGame("wordss");
        assertTrue(this.game.getRounds().isEmpty());
    }

    @Test
    @DisplayName("Beginning a game should return Game object")
    void testBeginGameShouldReturnGame() {
        Game returnedGame = this.game.beginGame("Wordss");
        assertNotNull(returnedGame);
    }

    @Test
    @DisplayName("If tries is less than five we should not end the game.")
    void testTriesLessThenFive() {
        this.game.beginGame("teste");
        this.game.makeAttempt("tests");
        assertNotEquals(GameStatus.GAME_ELIMINATED, this.game.getStatus());
        assertNotEquals(GameStatus.ROUND_WON, this.game.getStatus());
        assertNotEquals(GameStatus.GAME_STARTING, this.game.getStatus());
    }
    @Test
    @DisplayName("If tries more than five we should end the game.")
    void testTriesMoreThenFive() {
        this.game.beginGame("teste");
        this.game.makeAttempt("tests");
        this.game.makeAttempt("tests");
        this.game.makeAttempt("tests");
        this.game.makeAttempt("tests");
        this.game.makeAttempt("tests");
        this.game.makeAttempt("tests");
        assertEquals(GameStatus.GAME_ELIMINATED, this.game.getStatus());
    }

    @ParameterizedTest
    @MethodSource("provideLenghts")
    @DisplayName("According to the current word we should calculate the lenght of the next word")
    void testWordLenghts(String word, int nextLenght) {
        this.game.beginGame(word);
        assertEquals(nextLenght, this.game.provideLenghtOfNewWord());
    }

    static Stream<Arguments> provideLenghts() {
        return Stream.of(
                Arguments.of("fives", 6),
                Arguments.of("sixess", 7),
                Arguments.of("sevenss", 5)
        );
    }

    @Test
    @DisplayName("Guessing the word correctly should update the score")
    void testCalculateScore() {
        game.beginGame("hollos");
        game.makeAttempt("hollos");
        assertEquals(25, game.getScore());
    }

    @Test
    @DisplayName("Starting the game should set the status to GAME_STARTING")
    void testStartGameStatus() {
        game.beginGame("hollos");
        assertEquals(GameStatus.GAME_STARTING, game.getStatus());
    }

    @Test
    @DisplayName("After making an attempt the game status should change to GAME_PLAYING")
    void testPlayingGameStatus() {
        game.beginGame("holos");
        game.makeAttempt("hotos");
        assertEquals(GameStatus.GAME_PLAYING, game.getStatus());
    }
}