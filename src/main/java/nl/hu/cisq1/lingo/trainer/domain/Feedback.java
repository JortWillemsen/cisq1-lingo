package nl.hu.cisq1.lingo.trainer.domain;

import java.util.ArrayList;
import java.util.List;

public class Feedback {
    private String guess;
    private String wordToGuess;
    private List<Mark> feedback = new ArrayList<>();

    public Feedback(String guess, String wordToGuess) {
        this.guess = guess;
        this.wordToGuess = wordToGuess;
    }

    public List<Mark> calculate() {
        for(int i = 0; i < wordToGuess.length(); i++) {
            if(!Character.isLetter(guess.charAt(i))) {
                feedback.add(Mark.INVALID);
            } else if(guess.charAt(i) == wordToGuess.charAt(i)) {
                feedback.add(Mark.CORRECT);
            } else if(wordToGuess.indexOf(guess.charAt(i)) >= 0) {
                feedback.add(Mark.PRESENT);
            } else {
                feedback.add(Mark.INCORRECT);
            }
        }
        return feedback;
    }
}
