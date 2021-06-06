package pl.xtm.rekrutacja.exceptions;

import com.fasterxml.jackson.core.JsonParseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ExceptionControllerTest {

    private static final String WORD = "word";
    private static final String EXPECTED_BASE_MESSAGE = "Could not translate word: ";
    private static final String EXPECTED_BASE_MESSAGE_JSON_PARSE_EXCEPTION = "Could not parse JSON sent to the application exception: ";
    private static final String INJECTION_ATTEMPT = "\n \r \t";
    private static final String INJECTION_RESPONSE = "_ _ _";

    @Autowired
    ExceptionController exceptionController;
    @Test
    public void testJsonParseException() {
        ResponseEntity<Object> responseEntity = exceptionController.exception(new JsonParseException(null, WORD));
        assertEquals(responseEntity.getStatusCode(),HttpStatus.BAD_REQUEST);
        assertEquals(responseEntity.getBody(),EXPECTED_BASE_MESSAGE_JSON_PARSE_EXCEPTION + WORD);
    }

    @Test
    public void testTranslationNotFoundException() {

        ResponseEntity<Object> responseEntity = exceptionController.exception(new TranslationNotFoundException(WORD));

        assertEquals(responseEntity.getStatusCode(),HttpStatus.BAD_REQUEST);
        assertEquals(responseEntity.getBody(),(EXPECTED_BASE_MESSAGE + WORD));

    }
    @Test
    public void testLogSanitation() {

        ResponseEntity<Object> responseEntity = exceptionController.exception(new TranslationNotFoundException(INJECTION_ATTEMPT));

        assertTrue(responseEntity.getStatusCode().equals(HttpStatus.BAD_REQUEST));
        assertEquals(responseEntity.getBody(),EXPECTED_BASE_MESSAGE + INJECTION_RESPONSE);

    }
}