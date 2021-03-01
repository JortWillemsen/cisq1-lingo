package nl.hu.cisq1.lingo;

import nl.hu.cisq1.lingo.trainer.domain.Attempt;
import nl.hu.cisq1.lingo.trainer.domain.Feedback;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.domain.Round;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LingoApplication {
    public static void main(String[] args) {
        SpringApplication.run(LingoApplication.class, args);

        playGame();
    }

    public static void playGame() {
        Game game = new Game();
        Round round1 = game.nextRound("lingo");

        Attempt attempt1 = round1.makeAttempt("lingo");
        System.out.println(attempt1.getFeedback());

    }
}
