package nl.hu.cisq1.lingo.trainer.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FeedbackTest {

    @Test
    void testIsGuessedisTrueWhenWordsMatch() {
        Feedback feedback = new Feedback("tests", "tests");
        assertTrue(feedback.isGuessed());
    }

    @Test
    void testIsGuessedisFalseWhenWordsDoNotMatch() {
        Feedback feedback = new Feedback("teste", "tests");
        assertFalse(feedback.isGuessed());
    }
}