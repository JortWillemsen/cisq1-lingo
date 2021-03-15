package nl.hu.cisq1.lingo.trainer.domain;

import java.util.ArrayList;
import java.util.List;


public class Hint {
    private String wordToGuess;
    private List<Character> hint = new ArrayList<>();

    public Hint(String wordToGuess) {
        this.wordToGuess = wordToGuess;
        this.initialize();
    }

    private void initialize() {
        int size = wordToGuess.length();
        this.hint.add(wordToGuess.charAt(0));
        for(int i = 0; i < size - 1; i++) {
            this.hint.add('.');
        }
    }

    public Hint calculate(String attempt) {
        for(int i = 0; i < wordToGuess.length(); i++) {
            if(attempt.charAt(i) == this.hint.get(i) || Character.isLetter(this.hint.get(i))) {
                continue;
            }
            if(wordToGuess.charAt(i) == attempt.charAt(i)) {
                this.hint.set(i, wordToGuess.charAt(i));
            }
        }
        return this;
    }

    public String getHint() {
        StringBuilder sb = new StringBuilder();
        this.hint.forEach(sb::append);
        return sb.toString();
    }
}
