package pl.xtm.rekrutacja.repositories;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * Using a repository feels a bit forced, but shows the 3 layer architecture spring is pushing
 */
public class TranslationRepository {

    private JSONObject dictionary;

    public TranslationRepository(String path) throws JSONException {
        String fileContent = readFileFromResources(path);
        this.dictionary = parseToJson(fileContent);
    }

    public JSONObject getDictionary() {
        return dictionary;
    }

    private JSONObject parseToJson(String s) throws JSONException {
        return new JSONObject(s);
    }

    private String readFileFromResources(String fileName) {
        URL resource = TranslationRepository.class.getClassLoader().getResource(fileName);

        if (resource == null)
            throw new IllegalArgumentException("file is not found!");

        StringBuilder fileContent = new StringBuilder();

        BufferedReader bufferedReader = null;
        try {
            FileInputStream fis = new FileInputStream(resource.getFile());
            InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);

            bufferedReader = new BufferedReader((isr));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                fileContent.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return fileContent.toString();
    }


}
