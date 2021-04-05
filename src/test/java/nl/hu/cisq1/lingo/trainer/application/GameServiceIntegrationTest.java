package nl.hu.cisq1.lingo.trainer.application;

import nl.hu.cisq1.lingo.CiTestConfiguration;
import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.domain.Attempt;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.domain.GameStatus;
import nl.hu.cisq1.lingo.trainer.exception.GameNotFoundException;
import nl.hu.cisq1.lingo.trainer.exception.IllegalStatusException;
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
    void testShouldStartGameWhenNoGameActive() {
        assertDoesNotThrow(() ->
                this.gameService.startGame());
        assertNotNull(this.gameService.getActiveGame());
    }

    @Test
    void testStartGameShouldThrowWhenRoundActive() {
        this.gameService.startGame();
        assertThrows(IllegalStatusException.class,
                () -> this.gameService.startNextRound());
    }

    @Test
    void testStartNextRoundShouldReturnGame() {
        Game game = this.gameService.startGame();
        game.makeAttempt(game.getActiveRound().getWordToGuess());
        assertEquals(Game.class, this.gameService.startNextRound().getClass());
    }

    @Test
    void testStartNextRoundShouldTrowWhenRoundActive() {
        this.gameService.startGame();

        assertThrows(IllegalStatusException.class, () ->
                this.gameService.startNextRound());
    }

    @Test
    @DisplayName("When getting a game with id we should return a game object")
    void testShouldReturnGameWhenId() {
        this.gameService.startGame();
        Long id = this.gameService.getActiveGame().getId();

        assertDoesNotThrow(() -> this.gameService.getGameById(id));
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
        Attempt attempt = gameService.makeAttempt("apper");

        assertEquals("appe.", attempt.getHint());
    }

    @Test
    @DisplayName("Finishing a game should persist the game and change the state to GAME_FINISHED.")
    void testFinishGameShouldSetActiveGameToNull(){
        this.gameService.startGame();
        this.gameService.finishGame();

        assertThrows(GameNotFoundException.class, () ->
                this.gameService.getActiveGame());
    }
}