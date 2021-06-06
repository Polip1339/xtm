package pl.xtm.rekrutacja.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.xtm.rekrutacja.exceptions.TranslationNotFoundException;
import pl.xtm.rekrutacja.model.TextToTranslate;
import pl.xtm.rekrutacja.services.RankingService;
import pl.xtm.rekrutacja.services.TranslationService;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
public class TranslationControllerTest {
    private static final String EXAMPLE_MESSAGE = "{\"text\":\"Ala ma kota\"}";
    @MockBean
    TranslationService translationService;
    @MockBean
    RankingService rankingService;
    @Autowired
    TranslationController translationController;
    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(this.translationController).build();

    }

    @Test
    public void testCallingTranslationServiceRightNumberOfTimes() throws TranslationNotFoundException, Exception {


        mockMvc.perform(post("/translate")
                .contentType("application/json")
                .content(EXAMPLE_MESSAGE))
                .andExpect(status().isOk());

        verify(translationService, times(1)).translate(any(TextToTranslate.class));
        verifyNoMoreInteractions(translationService);
        verifyNoInteractions(rankingService);
    }

    @Test
    public void testCallingTranslationServiceWithQuotesRightNumberOfTimes() throws TranslationNotFoundException, Exception {


        mockMvc.perform(post("/translate")
                .contentType("application/json")
                .param("quotes", "true")
                .content(EXAMPLE_MESSAGE))
                .andExpect(status().isOk());

        verify(translationService, times(1)).translateQuotes(any(TextToTranslate.class));
        verifyNoMoreInteractions(translationService);
        verifyNoInteractions(rankingService);
    }

    @Test
    public void testCallingRankingServiceRightNumberOfTimes() throws Exception {

        mockMvc.perform(get("/ranking"))
                .andExpect(status().isOk());

        verify(rankingService, times(1)).getRanking();
        verifyNoInteractions(translationService);
        verifyNoMoreInteractions(rankingService);
    }


}