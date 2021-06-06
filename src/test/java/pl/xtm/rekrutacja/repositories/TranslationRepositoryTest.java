package pl.xtm.rekrutacja.repositories;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TranslationRepositoryTest {
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
    @Test
    public void checkIfConstructedCorrectly(){
        TranslationRepository translationRepository = new TranslationRepository("dictionary.json");

        translationRepository.getDictionary().equals(DICTIONARY);
    }
    @Test(expected = IllegalArgumentException.class)
    public void checkBehaviourWhenFileMissing(){
        TranslationRepository translationRepository = new TranslationRepository("noFile.json");

    }
    @Test(expected = JSONException.class)
    public void checkBehaviourWhenFileInvalid(){
        TranslationRepository translationRepository = new TranslationRepository("brokenDictionary.json");

    }
}