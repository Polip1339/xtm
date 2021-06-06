package pl.xtm.rekrutacja.services;


import org.json.JSONException;
import org.json.JSONObject;
import pl.xtm.rekrutacja.exceptions.TranslationNotFoundException;
import pl.xtm.rekrutacja.model.TextToTranslate;
import pl.xtm.rekrutacja.repositories.TranslationRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TranslationService {

    private RankingService rankingService;
    private TranslationRepository translationRepository;
    private JSONObject translationMap;

    public TranslationService(TranslationRepository translationRepository, RankingService rankingService) {
        this.rankingService = rankingService;
        this.translationRepository = translationRepository;
    }

    public String translate(TextToTranslate textTotranslate) throws TranslationNotFoundException, JSONException {
        // If datasource changed from file, one could rethink this if
        if (translationMap == null) {
            translationMap = translationRepository.getDictionary();
        }

        return translateWithDictionary(splitIntoList(textTotranslate.getText()), translationMap);
    }

    public String translateQuotes(TextToTranslate textTotranslate) throws TranslationNotFoundException, JSONException {
        if (translationMap == null) {
            translationMap = translationRepository.getDictionary();
        }
        List<String> wordsList = splitIntoList(textTotranslate.getText());

        return translateWithDictionary((removeQuotes(wordsList)), translationMap);

    }

    private List<String> removeQuotes(List<String> wordsList) {
        List<String> response = new ArrayList<>();
        wordsList.forEach(word -> response.add(word.replace("\"", "")));
        return response;
    }

    private List<String> splitIntoList(String textTotranslate) {
        return Arrays.asList(textTotranslate.split(" "));

    }

    private String translateWithDictionary(List<String> wordsList, JSONObject translationMap) throws TranslationNotFoundException {
        StringBuilder stringBuilder = new StringBuilder();
        //decided a for loop would be best since it uses a single translation map
        for (int i = 0; i < wordsList.size(); i++) {
            String word = wordsList.get(i);
            try {
                stringBuilder.append((String) translationMap.get(word));
                rankingService.increaseRank(word);
                if (i != wordsList.size() - 1) {
                    stringBuilder.append(" ");
                }
            } catch (JSONException e) {
                throw new TranslationNotFoundException(word);
            }
        }
        return stringBuilder.toString();

    }


}
