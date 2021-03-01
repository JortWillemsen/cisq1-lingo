package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.words.domain.Word;

import java.util.List;
import java.util.UUID;

public class Game {
    private UUID id;
    private int score;
    private List<Round> rounds;

    public Game() { }

    public Round nextRound(Word newWord) {
        this.rounds.add(new Round(newWord));

        return this.rounds.get(this.rounds.size() -1);
    }
}
