package nl.hu.cisq1.lingo.trainer.presentation;

import nl.hu.cisq1.lingo.trainer.exception.GameNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Map<String, String>> exception(Exception e) {
        HashMap<String, String> map = new HashMap<>();
        map.put("Error", e.getMessage());
        return new ResponseEntity<>(map, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = GameNotFoundException.class)
    public ResponseEntity<Map<String, String>> gameNotFoundException(Exception e) {
        HashMap<String, String> map = new HashMap<>();
        map.put("Error", e.getMessage());
        return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = IllegalStateException.class)
    public ResponseEntity<Map<String, String>> illegalStateException(Exception e) {
        HashMap<String, String> map = new HashMap<>();
        map.put("Error", e.getMessage());
        return new ResponseEntity<>(map, HttpStatus.CONFLICT);
    }

}
