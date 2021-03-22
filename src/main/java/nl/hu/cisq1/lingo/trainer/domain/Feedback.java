package nl.hu.cisq1.lingo.trainer.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Feedback")
public class Feedback {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column
    private String guess;
    private String wordToGuess;

    @ElementCollection
    private List<Mark> marks = new ArrayList<>();

    public Feedback() { }
    public Feedback(String guess, String wordToGuess) {
        this.guess = guess;
        this.wordToGuess = wordToGuess;
    }

    public List<Mark> calculate() {
        this.marks.clear();
        for(int i = 0; i < this.wordToGuess.length(); i++) {
            if(!Character.isLetter(this.guess.charAt(i))) {
                this.marks.add(Mark.INVALID);
            } else if(this.guess.charAt(i) == this.wordToGuess.charAt(i)) {
                this.marks.add(Mark.CORRECT);
            } else if(this.wordToGuess.indexOf(this.guess.charAt(i)) >= 0) {
                this.marks.add(Mark.PRESENT);
            } else {
                this.marks.add(Mark.INCORRECT);
            }
        }
        return this.marks;
    }

    public boolean isGuessed() {
        this.calculate();
        return this.guess.equals(wordToGuess);
    }
}
