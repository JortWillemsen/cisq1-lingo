package nl.hu.cisq1.lingo.trainer.application;

import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.domain.Attempt;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.exception.GameNotFoundException;
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

    public Attempt makeAttempt(String guess) {
        Game game = this.getActiveGame();
        Attempt attempt = game.makeAttempt(guess);
        this.persistGame(game);
        return attempt;
    }

    public Game getActiveGame() {
        return this.gameRepository.getGameByFinished(false)
                .orElseThrow(() -> new GameNotFoundException("No active game found..."));
    }


}
