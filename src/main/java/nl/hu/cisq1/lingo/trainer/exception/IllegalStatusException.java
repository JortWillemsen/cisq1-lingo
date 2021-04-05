package nl.hu.cisq1.lingo.trainer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class IllegalStatusException extends RuntimeException {
    public IllegalStatusException(String message) {
        super(message);
    }
}
