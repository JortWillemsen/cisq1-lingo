package nl.hu.cisq1.lingo.trainer.application;

import ch.qos.logback.core.encoder.EchoEncoder;
import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.domain.Attempt;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.domain.GameStatus;
import nl.hu.cisq1.lingo.trainer.exception.GameNotFoundException;
import nl.hu.cisq1.lingo.trainer.exception.IllegalStatusException;
import nl.hu.cisq1.lingo.words.application.WordService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

class GameServiceTest {
    WordService wordService = mock(WordService.class);
    SpringGameRepository gameRepository = mock(SpringGameRepository.class);
    GameService gameService;
    Game testGame = new Game();

    @BeforeEach
    void initialize() {
        when(wordService.provideRandomWord(5)).thenReturn("appel");
        when(gameRepository.getGameByFinished(false)).thenReturn(Optional.of(testGame));
        when(gameRepository.getGameById(1L)).thenReturn(Optional.of(testGame));

        gameService = new GameService(gameRepository, wordService);
    }

    @AfterEach
    void teardown() {
        clearInvocations(wordService, gameRepository);
    }

    @Test
    @DisplayName("When starting a game, we should return a game object")
    void testStartGameShouldReturnGame() {
        when(gameRepository.getGameByFinished(false)).thenReturn(Optional.empty());
        Game game = gameService.startGame();

        verify(wordService, times(1)).provideRandomWord(5);
        verify(gameRepository, times(1)).save(any(Game.class));
        assertEquals(GameStatus.GAME_PLAYING, game.getStatus());
    }

    @Test
    @DisplayName("When starting a game, we should throw when there is a active game present")
    void testStartGameShouldThrowWhenActiveGame() {
        assertThrows(IllegalStatusException.class, () ->
                this.gameService.startGame());
    }

    @Test
    @DisplayName("When getting a game with id 1 we should return a game object")
    void testShouldReturnGameWhenId() {
        assertDoesNotThrow(() -> gameService.getGameById(1L));
    }

    @Test
    @DisplayName("Should throw GameNotFound exception when there is not active game")
    void testShouldThrowWhenNoActiveGame() {
        when(gameRepository.getGameByFinished(false)).thenReturn(Optional.empty());

        assertThrows(GameNotFoundException.class, () -> gameService.getActiveGame());
    }

    @Test
    @DisplayName("Should throw GameNotFound exception when there is not game with certain id")
    void testShouldThrowWhenNoGameWithId() {
        when(gameRepository.getGameById(anyLong())).thenReturn(Optional.empty());

        assertThrows(GameNotFoundException.class, () -> gameService.getGameById(anyLong()));
    }

    @Test
    @DisplayName("Should return a game object when getting the active game")
    void testGetActiveGameShouldReturnGame() {
        assertDoesNotThrow(() -> gameService.getActiveGame());
    }

    @Test
    @DisplayName("Making an attempt should return an attempt")
    void testMakeAttemptShouldReturnAttempt() {
        testGame.beginGame("appel");
        Attempt attempt = gameService.makeAttempt("apper");

        verify(gameRepository, times(1)).getGameByFinished(false);
        verify(gameRepository, times(1)).save(any(Game.class));
        assertEquals("appe.", attempt.getHint());
    }

    @Test
    @DisplayName("Finishing a game should persist the game and change the state to GAME_FINISHED.")
    void testFinishGameShouldSetStateAndPersist(){
        gameService.finishGame();

        verify(gameRepository, times(1)).save(testGame);
        assertEquals(GameStatus.GAME_ELIMINATED, testGame.getStatus());
    }

    @Test
    @DisplayName("StartNextRound should return a Game")
    void testStartNextRoundShouldReturnGame() {
        when(wordService.provideRandomWord(6)).thenReturn("appels");

        testGame.beginGame("worde");
        testGame.makeAttempt("worde");
        Game game = gameService.startNextRound();
        assertEquals(Game.class, game.getClass());
    }

    @Test
    @DisplayName("GetGameByID should return a Game")
    void testGetGameByIDShouldReturnGame() {
        Game game = gameService.getGameById(1L);
        assertEquals(Game.class, game.getClass());
    }

    @ParameterizedTest
    @MethodSource("provideLenghts")
    @DisplayName("According to the current word we should calculate the lenght of the next word")
    void testWordLenghts(String word, int nextLenght) {
        when(wordService.provideRandomWord(nextLenght)).thenReturn(word);
        testGame.beginGame(word);
        testGame.makeAttempt(word);
        gameService.startNextRound();
        verify(wordService, times(1)).provideRandomWord(nextLenght);
        verify(gameRepository, times(1)).save(any(Game.class));
    }

    static Stream<Arguments> provideLenghts() {
        return Stream.of(
                Arguments.of("fives", 6),
                Arguments.of("sixess", 7),
                Arguments.of("sevenss", 5),
                Arguments.of("fives", 6),
                Arguments.of("sixess", 7),
                Arguments.of("sevenss", 5)
        );
    }
}