package nl.hu.cisq1.lingo.trainer.service;

import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.words.application.WordService;
import org.springframework.stereotype.Service;

@Service
public class GameService {
    private final SpringGameRepository gameRepository;
    private final WordService wordService;
    Game game;

    public GameService(SpringGameRepository gameRepository, WordService wordService) {
        this.gameRepository = gameRepository;
        this.wordService = wordService;
    }


    private void persistGame(Game game) {
        gameRepository.save(game);
    }

    public Game startGame() {
        game = new Game();
        game.beginGame(this.wordService.provideRandomWord(5));
        //this.gameRepository.save(game);
        return game;
    }

    public Game getGame() {
        return game;
    }
}
