package pl.xtm.rekrutacja.exceptions;

import com.fasterxml.jackson.core.JsonParseException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.regex.Pattern;

@Log4j2
@ControllerAdvice
public class ExceptionController {

    private static final Pattern escapeCharacters = Pattern.compile("[\n|\r|\t]");

    @ExceptionHandler(value = TranslationNotFoundException.class)
    public ResponseEntity<Object> exception(TranslationNotFoundException exception) {
        StringBuilder stringBuilder = new StringBuilder("Could not translate word: ");
        stringBuilder.append(sanitizeLog(exception.getExceptionMessage()));
        String message = stringBuilder.toString();
        log.error(message);
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = JsonParseException.class)
    public ResponseEntity<Object> exception(JsonParseException exception) {
        exception.printStackTrace();
        return new ResponseEntity<>("Could not parse JSON sent to the application exception: " + exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // basic log sanitization, prevents users from log injection.
    private String sanitizeLog(String unsatized) {
        return escapeCharacters.matcher(unsatized).replaceAll("_");
    }

}
