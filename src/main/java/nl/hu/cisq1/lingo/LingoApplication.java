package nl.hu.cisq1.lingo;

import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.domain.*;
import nl.hu.cisq1.lingo.trainer.service.GameService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LingoApplication {
    public static void main(String[] args) {
        SpringApplication.run(LingoApplication.class, args);

        //playGame();
    }

    public static void playGame() {
        Game game = new Game();
        System.out.println(game.beginGame("haren"));

        Attempt attempt1 = game.makeAttempt("haler");
        System.out.println(attempt1.getHint());

        Attempt attempt2 = game.makeAttempt("horen");
        System.out.println(attempt2.getHint());

        Attempt attempt3 = game.makeAttempt("haren");
        System.out.println(attempt3.getHint());
    }
}
