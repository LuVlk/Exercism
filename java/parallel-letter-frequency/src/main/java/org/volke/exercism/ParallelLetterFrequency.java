package org.volke.exercism;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ParallelLetterFrequency {

    private final String[] texts;

    public ParallelLetterFrequency(String[] texts) {
        this.texts = texts;
    }

    public Map<Character, Integer> countLetters() {
        ConcurrentMap<Character, Integer> counts = new ConcurrentHashMap<>();

        Arrays.stream(texts)
                .parallel()
                .flatMap(text -> text.chars().mapToObj(c -> (char) c))
                .map(c -> Character.toLowerCase(c))
                .filter(c -> Character.isAlphabetic(c))
                .forEach(c -> counts.merge(c, 1, Integer::sum));

        return counts;
    }
}
