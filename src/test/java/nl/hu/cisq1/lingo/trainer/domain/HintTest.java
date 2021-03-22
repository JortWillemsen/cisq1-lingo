package nl.hu.cisq1.lingo.trainer.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HintTest {
    private Game game;
    private String word = "teste";

    @BeforeEach
    void initialize() {
        this.game = new Game();
        this.game.beginGame(this.word);
    }
}