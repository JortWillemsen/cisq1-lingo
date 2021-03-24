package nl.hu.cisq1.lingo.trainer.presentation;

import nl.hu.cisq1.lingo.trainer.domain.Attempt;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.domain.Round;
import nl.hu.cisq1.lingo.trainer.presentation.dto.AttemptRequestDto;
import nl.hu.cisq1.lingo.trainer.presentation.dto.AttemptResponseDto;
import nl.hu.cisq1.lingo.trainer.presentation.dto.RoundResponseDto;
import nl.hu.cisq1.lingo.trainer.presentation.dto.StartGameResponseDto;
import nl.hu.cisq1.lingo.trainer.application.GameService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lingo")
public class GameController {
    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/start")
    public StartGameResponseDto startGame() {
        Game game = this.gameService.startGame();
        return new StartGameResponseDto(game.getActiveRound().getHint().getHint());
    }

    @PutMapping("/attempt")
    public AttemptResponseDto makeAttempt(@RequestBody AttemptRequestDto requestDto) {
        Attempt attempt = this.gameService.makeAttempt(requestDto.guess);
        return new AttemptResponseDto(attempt.getHint(), attempt.getGuess(), attempt.getFeedback());
    }

    @GetMapping("/round")
    public RoundResponseDto getRoundInformation() {
        Round round = this.gameService.getActiveGame().getActiveRound();
        return new RoundResponseDto(round.getTries(), round.isGuessed(), round.getHint().getHint(), round.getWordToGuess(), round.getAttempts());
    }
}
