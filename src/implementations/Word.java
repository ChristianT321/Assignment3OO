package implementations;

import java.io.Serializable;
import java.util.LinkedList;

public class Word implements Comparable<Word>, Serializable {

    private String word;
    private LinkedList<WordLocation> locations;

    public Word(String word) {
        this.word = word.toLowerCase();
        this.locations = new LinkedList<>();
    }

    public String getWord() {
        return word;
    }

    public LinkedList<WordLocation> getLocations() {
        return locations;
    }

    public void addLocation(String fileName, int lineNumber) {
        for (WordLocation location : locations) {
            if (location.getFileName().equals(fileName)) {
                location.addLineNumber(lineNumber);
                return;
            }
        }
        WordLocation newLocation = new WordLocation(fileName);
        newLocation.addLineNumber(lineNumber);
        locations.add(newLocation);
    }

    public int getTotalFrequency() {
        int total = 0;
        for (WordLocation location : locations) {
            total += location.getFrequency();
        }
        return total;
    }

    @Override
    public int compareTo(Word other) {
        return this.word.compareTo(other.word);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Word) {
            Word other = (Word) o;
            return this.word.equals(other.word);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return word.hashCode();
    }

    @Override
    public String toString() {
        return word + " -> " + locations.toString();
    }
}