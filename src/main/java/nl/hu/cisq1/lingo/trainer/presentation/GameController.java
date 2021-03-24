package nl.hu.cisq1.lingo.trainer.presentation;

import nl.hu.cisq1.lingo.trainer.domain.Attempt;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.domain.Round;
import nl.hu.cisq1.lingo.trainer.presentation.dto.*;
import nl.hu.cisq1.lingo.trainer.application.GameService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lingo")
public class GameController {
    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/game")
    public StartRoundResponseDto startGame() {
        Game game = this.gameService.startGame();
        return new StartRoundResponseDto(game.getActiveRound().getHint().getHint());
    }

    @PostMapping("/round")
    public StartRoundResponseDto startNextRound() {
        Game game = this.gameService.startNextRound();
        return new StartRoundResponseDto(game.getActiveRound().getHint().getHint());
    }

    @PutMapping("/attempt")
    public AttemptResponseDto makeAttempt(@RequestBody AttemptRequestDto requestDto) {
        Attempt attempt = this.gameService.makeAttempt(requestDto.guess);
        return new AttemptResponseDto(attempt.getHint(), attempt.getGuess(), attempt.getFeedback());
    }

    @PatchMapping("/game/active/finish")
    public void finishGame(){
        this.gameService.finishGame();
    }

    @GetMapping("/round")
    public RoundResponseDto getRoundInformation() {
        Round round = this.gameService.getActiveGame().getActiveRound();
        return new RoundResponseDto(round.getTries(), round.isGuessed(), round.getHint().getHint(), round.getWordToGuess(), round.getAttempts());
    }

    @GetMapping("/game/active")
    public GameResponseDto getActiveGameInformation() {
        Game game = this.gameService.getActiveGame();
        return new GameResponseDto(
                game.getId(),
                game.getScore(),
                game.getRounds().size(),
                this.createRoundResponse(game.getActiveRound()),
                game.getStatus(),
                game.isFinished()
        );
    }

    @GetMapping("/game/{id}")

    public GameResponseDto getGameInformation(@PathVariable Long id) {
        Game game = this.gameService.getGameById(id);
        return new GameResponseDto(
                game.getId(),
                game.getScore(),
                game.getRounds().size(),
                this.createRoundResponse(game.getActiveRound()),
                game.getStatus(),
                game.isFinished()
        );
    }

    private RoundResponseDto createRoundResponse(Round round) {
        return new RoundResponseDto(
                round.getTries(),
                round.isGuessed(),
                round.getHint().getHint(),
                round.getWordToGuess(),
                round.getAttempts()
        );
    }
}
