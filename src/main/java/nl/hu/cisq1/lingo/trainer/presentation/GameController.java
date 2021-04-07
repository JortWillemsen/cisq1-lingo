package nl.hu.cisq1.lingo.trainer.presentation;

import nl.hu.cisq1.lingo.trainer.domain.Attempt;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.domain.Round;
import nl.hu.cisq1.lingo.trainer.presentation.dto.*;
import nl.hu.cisq1.lingo.trainer.application.GameService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lingo/game")
public class GameController {
    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping
    public StartRoundResponseDto startGame() {
        Game game = this.gameService.startGame();
        return new StartRoundResponseDto(game.getId(), game.getActiveRound().getHint().getHint());
    }

    @PostMapping("/{id}/round")
    public StartRoundResponseDto startNextRound(@PathVariable Long id) {
        Game game = this.gameService.startNextRound(id);
        return new StartRoundResponseDto(game.getId(), game.getActiveRound().getHint().getHint());
    }

    @PostMapping("/{id}/attempt")
    public AttemptResponseDto makeAttempt(@PathVariable Long id, @RequestBody AttemptRequestDto requestDto) {
        Attempt attempt = this.gameService.makeAttempt(requestDto.guess, id);
        return new AttemptResponseDto(id, attempt.getHint(), attempt.getGuess(), attempt.getFeedback());
    }

    @PatchMapping("/{id}/finish")
    public void finishGame(@PathVariable Long id){
        this.gameService.finishGame(id);
    }

    @GetMapping("/{id}/round")
    public RoundResponseDto getRoundInformation(@PathVariable Long id) {
        Round round = this.gameService.getGameById(id).getActiveRound();
        return new RoundResponseDto(id, round.getTries(), round.isGuessed(), round.getHint().getHint(), round.getWordToGuess(), round.getAttempts());
    }

    @GetMapping("/{id}")
    public GameResponseDto getGameInformation(@PathVariable Long id) {
        Game game = this.gameService.getGameById(id);
        return new GameResponseDto(
                game.getId(),
                game.getScore(),
                game.getRounds().size(),
                this.createRoundResponse(game, game.getActiveRound()),
                game.getStatus(),
                game.isFinished()
        );
    }

    private RoundResponseDto createRoundResponse(Game game, Round round) {
        return new RoundResponseDto(
                game.getId(),
                round.getTries(),
                round.isGuessed(),
                round.getHint().getHint(),
                round.getWordToGuess(),
                round.getAttempts()
        );
    }
}
