package pl.xtm.rekrutacja.services;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import pl.xtm.rekrutacja.exceptions.TranslationNotFoundException;
import pl.xtm.rekrutacja.model.TextToTranslate;
import pl.xtm.rekrutacja.repositories.TranslationRepository;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TranslationServiceTest {
    private static final String DICTIONARY_STRING = "{\n" +
            "\t\"Ala\": \"Alice\",\n" +
            "\t\"ma\": \"has\",\n" +
            "\t\"kota\": \"a cat\",\n" +
            "\t\"jesteś\": \"you are\",\n" +
            "\t\"sterem\": \"the helm\",\n" +
            "\t\"białym\": \"white\",\n" +
            "\t\"żołnierzem\": \"soldier\",\n" +
            "\t\"nosisz\": \"wear\",\n" +
            "\t\"spodnie\": \"trousers\",\n" +
            "\t\"więc\": \"so\",\n" +
            "\t\"walcz\": \"fight\"\n" +
            "}";
    private static final JSONObject DICTIONARY = new JSONObject(DICTIONARY_STRING);
    private static final String EXAMPLE_BASIC_TEXT = "Ala ma kota jesteś sterem białym żołnierzem nosisz spodnie więc walcz";
    private static final String EXAMPLE_QUOTED_TEXT = "\"Ala\" \"ma\" \"kota\" \"jesteś\" \"sterem\" \"białym\" \"żołnierzem\" \"nosisz\" \"spodnie\" \"więc\" \"walcz\"";
    private static final String EXAMPLE_RESPONSE = "Alice has a cat you are the helm white soldier wear trousers so fight";

    @Autowired
    TranslationService translationService;
    @MockBean
    TranslationRepository translationRepository;

    @MockBean
    RankingService rankingService;

    @Before
    public void setup() {
        when(translationRepository.getDictionary()).thenReturn(DICTIONARY);
    }

    @Test
    public void checkSingleWordTranslation() throws TranslationNotFoundException {
        String response = translationService.translate(createTextToTranslate("Ala"));
        assertTrue(response.equals("Alice"));
    }

    @Test
    public void checkBasicTranslation() throws TranslationNotFoundException {
        String response = translationService.translate(createTextToTranslate(EXAMPLE_BASIC_TEXT));
        assertTrue(response.equals(EXAMPLE_RESPONSE));
    }

    @Test
    public void checkTranslationWithQuotes() throws TranslationNotFoundException {
        String response = translationService.translateQuotes(createTextToTranslate(EXAMPLE_QUOTED_TEXT));
        assertTrue(response.equals(EXAMPLE_RESPONSE));
    }

    @Test(expected = TranslationNotFoundException.class)
    public void checkBasicTranslationFailedWithQuotes() throws TranslationNotFoundException {
        String response = translationService.translate(createTextToTranslate(EXAMPLE_QUOTED_TEXT));
    }

    @Test(expected = TranslationNotFoundException.class)
    public void checkUnknownWordTranslation() throws TranslationNotFoundException {
        String response = translationService.translate(createTextToTranslate("RANDOM"));
    }

    private TextToTranslate createTextToTranslate(String text) {
        TextToTranslate textToTranslate = new TextToTranslate();
        textToTranslate.setText(text);
        return textToTranslate;
    }

}