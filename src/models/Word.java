package models;

import java.io.Serializable;
import java.util.*;

public class Word implements Comparable<Word>, Serializable {
    private static final long serialVersionUID = 1L;

    private String word;
    private Map<String, List<Integer>> occurrences;

    public Word(String word) {
        this.word = word.toLowerCase();
        this.occurrences = new HashMap<>();
    }

//    public void addOccurrence(String filename, int lineNumber) {
//        occurrences.computeIfAbsent(filename, k -> new ArrayList<>()).add(lineNumber);
//    }
    
    public void addOccurrence(String filename, int lineNumber) {
        List<Integer> lines = occurrences.computeIfAbsent(filename, k -> new ArrayList<>());
        if (!lines.contains(lineNumber)) {
            lines.add(lineNumber);
        }
    }

    public String getWord() {
        return word;
    }

    public Map<String, List<Integer>> getOccurrences() {
        return occurrences;
    }

    public int getTotalFrequency() {
        return occurrences.values().stream().mapToInt(List::size).sum();
    }

    @Override
    public int compareTo(Word other) {
        return this.word.compareTo(other.word);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Word && this.word.equals(((Word) o).word);
    }

    @Override
    public int hashCode() {
        return word.hashCode();
    }
}

