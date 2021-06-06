package pl.xtm.rekrutacja.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RankingServiceTest {
    private static final String WORD = "word";
    private static final String WORD_2 = "Alice";
    @Autowired
    RankingService rankingService;

    @Before
    public void setup() {
        rankingService.clearRanking();
    }

    @Test
    public void addWordToRanking() {
        rankingService.increaseRank(WORD);

        Map<String, Integer> ranking = rankingService.getRanking();
        assertTrue(ranking.containsKey(WORD));
        assertTrue(ranking.get(WORD).equals(1));

    }

    @Test
    public void addWordTwice() {
        rankingService.increaseRank(WORD);
        rankingService.increaseRank(WORD);

        Map<String, Integer> ranking = rankingService.getRanking();
        assertTrue(ranking.containsKey(WORD));
        assertTrue(ranking.get(WORD).equals(2));

    }

    @Test
    public void seeIfadditionalWordsArePresent() {
        rankingService.increaseRank(WORD);
        Map<String, Integer> ranking = rankingService.getRanking();
        assertFalse(ranking.containsKey(WORD_2));
        assertTrue(ranking.get(WORD).equals(1));
        assertTrue(ranking.size() == 1);
    }

    @Test
    public void testClearingRanking() {
        rankingService.increaseRank(WORD);
        rankingService.increaseRank(WORD_2);
        rankingService.clearRanking();

        Map<String, Integer> ranking = rankingService.getRanking();

        assertTrue(ranking.size() == 0);
    }

    @Test
    public void testSortingRanking() {
        rankingService.increaseRank(WORD_2);
        rankingService.increaseRank(WORD);
        rankingService.increaseRank(WORD);

        Map<String, Integer> ranking = rankingService.getRanking();
        Map.Entry<String, Integer> entry = ranking.entrySet().iterator().next();
        assertTrue(entry.getKey().equals(WORD));

        rankingService.increaseRank(WORD_2);
        rankingService.increaseRank(WORD_2);

        ranking = rankingService.getRanking();
        entry = ranking.entrySet().iterator().next();
        assertTrue(entry.getKey().equals(WORD_2));

    }
}