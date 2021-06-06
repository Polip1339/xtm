package pl.xtm.rekrutacja.config;


import org.json.JSONException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.xtm.rekrutacja.repositories.TranslationRepository;
import pl.xtm.rekrutacja.services.RankingService;
import pl.xtm.rekrutacja.services.TranslationService;

/**
 * Configuration class is a bit of an overkill but i prefer these to just annotations especially for larger apps.
 */
@Configuration
public class ConfigMain {
    @Value("${dictionaryFile}")
    String pathToDictionary;

    @Bean
    public TranslationService translationService() throws JSONException {
        return new TranslationService(translationRepository(), rankingService());
    }

    @Bean
    public TranslationRepository translationRepository() throws JSONException {
        return new TranslationRepository(pathToDictionary);
    }

    @Bean
    public RankingService rankingService() {
        return new RankingService();
    }

}
