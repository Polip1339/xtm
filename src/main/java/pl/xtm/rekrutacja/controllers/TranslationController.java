package pl.xtm.rekrutacja.controllers;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.xtm.rekrutacja.exceptions.TranslationNotFoundException;
import pl.xtm.rekrutacja.model.TextToTranslate;
import pl.xtm.rekrutacja.services.RankingService;
import pl.xtm.rekrutacja.services.TranslationService;

import java.util.Map;


@RestController
@RequestMapping("/")
public class TranslationController {

    @Autowired
    TranslationService translationService;
    @Autowired
    RankingService rankingService;

    @PostMapping("translate")
    public String translate(@RequestBody final TextToTranslate textToTranslate, @RequestParam(required = false) boolean quotes) throws TranslationNotFoundException, JSONException {
        if (quotes) {
            return translationService.translateQuotes(textToTranslate);
        } else {
            return translationService.translate(textToTranslate);
        }
    }

    @GetMapping("ranking")
    public Map<String, Integer> translate() {

        return rankingService.getRanking();

    }

}
