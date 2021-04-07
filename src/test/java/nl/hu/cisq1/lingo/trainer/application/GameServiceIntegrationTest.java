package nl.hu.cisq1.lingo.trainer.application;

import nl.hu.cisq1.lingo.CiTestConfiguration;
import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.domain.Attempt;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.domain.GameStatus;
import nl.hu.cisq1.lingo.trainer.exception.GameNotFoundException;
import nl.hu.cisq1.lingo.trainer.exception.IllegalStatusException;
import nl.hu.cisq1.lingo.trainer.exception.InvalidAttemptException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import javax.transaction.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@SpringBootTest
@Import(CiTestConfiguration.class)
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GameServiceIntegrationTest {

    private Game testGame;

    @Autowired
    private GameService gameService;

    @Autowired
    private SpringGameRepository gameRepository;

    @BeforeAll
    void initialize() {
        this.testGame = new Game();
    }

    @Test
    void testStartGameShouldThrowWhenRoundActive() {
        Game game = this.gameService.startGame();
        assertThrows(IllegalStatusException.class,
                () -> this.gameService.startNextRound(game.getId()));
    }

    @Test
    void testStartNextRoundShouldReturnGame() {
        Game game = this.gameService.startGame();
        game.makeAttempt(game.getActiveRound().getWordToGuess());
        assertEquals(Game.class, this.gameService.startNextRound(game.getId()).getClass());
    }

    @Test
    void testStartNextRoundShouldTrowWhenRoundActive() {
        Game game = this.gameService.startGame();

        assertThrows(IllegalStatusException.class, () ->
                this.gameService.startNextRound(game.getId()));
    }

    @Test
    @DisplayName("Should throw GameNotFound exception when there is not game with certain id")
    void testShouldThrowWhenNoGameWithId() {
        assertThrows(GameNotFoundException.class, () -> gameService.getGameById(1L));
    }

    @Test
    @DisplayName("Making an attempt should return an attempt")
    void testMakeAttemptShouldReturnAttempt() {
        testGame.beginGame("appel");
        this.gameRepository.save(testGame);
        Attempt attempt = gameService.makeAttempt("anaal", testGame.getId());

        assertEquals("a...l", attempt.getHint());
    }

    @Test
    @DisplayName("Making an attempt with an invalid word should throw exception")
    void testShouldThrowWhenInvalidAttempt() {
        assertThrows(InvalidAttemptException.class,
                () -> gameService.makeAttempt("anius", testGame.getId()));
    }

    @Test
    @DisplayName("Finishing a game should persist the game and change the state to GAME_FINISHED.")
    void testFinishGameShouldSetActiveGameToNull(){
        Game game = this.gameService.startGame();
        this.gameService.finishGame(game.getId());

        Game foundGame = this.gameRepository.getGameById(game.getId()).orElseThrow(
                () -> new GameNotFoundException("Game not found"));

        assertTrue(foundGame.isFinished());
    }
}