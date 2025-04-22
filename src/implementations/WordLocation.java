package implementations;

import java.io.Serializable;
import java.util.LinkedList;

public class WordLocation implements Serializable {

    private String fileName;
    private LinkedList<Integer> lines;

    public WordLocation(String fileName) {
        this.fileName = fileName;
        this.lines = new LinkedList<>();
    }

    public String getFileName() {
        return fileName;
    }

    public LinkedList<Integer> getLineNumbers() {
        return lines;
    }

    public void addLineNumber(int line) {
        if (!lines.contains(line)) {
            lines.add(line);
        }
    }

    public int getFrequency() {
        return lines.size();
    }

    public String toString() {
        return fileName + ": " + lines;
    }
}
