package nl.hu.cisq1.lingo.trainer.domain;

import java.util.ArrayList;
import java.util.List;


public class Hint {
    private String wordToGuess;
    private List<Character> characters = new ArrayList<>();

    public Hint(String wordToGuess) {
        this.wordToGuess = wordToGuess;
        this.initialize();
    }

    private void initialize() {
        int size = wordToGuess.length();
        this.characters.add(wordToGuess.charAt(0));
        for (int i = 0; i < size - 1; i++) {
            this.characters.add('.');
        }
    }

    public Hint calculate(String attempt) {
        for (int i = 0; i < wordToGuess.length(); i++) {
            if (Character.isLetter(this.characters.get(i))) {
                continue;
            }
            if (wordToGuess.charAt(i) == attempt.charAt(i)) {
                this.characters.set(i, wordToGuess.charAt(i));
            }
        }
        return this;
    }

    public String getHint() {
        StringBuilder sb = new StringBuilder();
        this.characters.forEach(sb::append);
        return sb.toString();
    }
}
