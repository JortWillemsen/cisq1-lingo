package nl.hu.cisq1.lingo.trainer.presentation;

import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.presentation.dto.StartGameDto;
import nl.hu.cisq1.lingo.trainer.service.GameService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lingo")
public class GameController {
    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/start")
    public StartGameDto startGame() {
        Game game = gameService.startGame();
        return new StartGameDto(game.getActiveRound().getHint().getHint());
    }

    @GetMapping("/")
    public Game getGame() {
        return gameService.getGame();
    }
}
