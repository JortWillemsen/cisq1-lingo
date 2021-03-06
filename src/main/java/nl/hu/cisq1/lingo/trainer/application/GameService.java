package nl.hu.cisq1.lingo.trainer.application;

import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.domain.Attempt;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.exception.GameNotFoundException;
import nl.hu.cisq1.lingo.trainer.exception.IllegalStatusException;
import nl.hu.cisq1.lingo.words.application.WordService;
import org.springframework.stereotype.Service;

@Service
public class GameService {
    private final SpringGameRepository gameRepository;
    private final WordService wordService;

    public GameService(SpringGameRepository gameRepository, WordService wordService) {
        this.gameRepository = gameRepository;
        this.wordService = wordService;
    }

    private void persistGame(Game game) {
        this.gameRepository.save(game);
    }

    public Game startGame() {
        Game game = new Game();
        game.beginGame(this.wordService.provideRandomWord(5));
        this.persistGame(game);
        return game;
    }

    public Attempt makeAttempt(String guess, Long id) {
        wordService.wordExists(guess);
        Game game = this.getGameById(id);
        Attempt attempt = game.makeAttempt(guess);
        this.persistGame(game);
        return attempt;
    }

    public Game startNextRound(Long id) {
        Game game = this.getGameById(id);
        game.nextRound(wordService.provideRandomWord(game.provideLenghtOfNewWord()));
        this.persistGame(game);
        return game;
    }

    public Game getGameById(Long id) {
        return this.gameRepository.getGameById(id)
                .orElseThrow(() -> new GameNotFoundException("No game found with id: " + id.toString()));
    }

    public void finishGame(Long id) {
        Game game = this.getGameById(id);
        game.finishGame();
        this.persistGame(game);
    }


}
