package nl.hu.cisq1.lingo.trainer.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Game {
    private UUID id;
    private int score;
    private List<Round> rounds = new ArrayList<>();

    public Game() { }

    public Round nextRound(String newWord) {
        this.rounds.add(new Round(newWord));
        return this.rounds.get(this.rounds.size() -1);
    }
}
