package pl.xtm.rekrutacja.services;

import lombok.Getter;

import java.util.*;

@Getter
public class RankingService {
    Map<String, Integer> ranking;

    public RankingService() {
        ranking = new TreeMap<>();
    }

    public void increaseRank(String word) {
        if (ranking.containsKey(word)) {
            ranking.put(word, ranking.get(word) + 1);
        } else {
            ranking.put(word, 1);
        }
    }

    public Map<String, Integer> getRanking() {
        Map<String, Integer> sortedMap = sortRanking();
        return sortedMap;
    }

    public void clearRanking() {
        ranking = new TreeMap<>();
    }

    private Map<String, Integer> sortRanking() {
        List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(ranking.entrySet());
        Collections.sort(list, (o1, o2) -> o2.getValue().compareTo(o1.getValue()));
        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }
}

