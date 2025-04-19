package appDomain;

import implementations.BSTree;
import models.Word;
import utilities.Iterator;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class WordTracker {
    private static final String SERIAL_FILE = "repository.ser";

    public static void main(String[] args) {
        if (args.length < 2 || (!args[1].equals("-pf") && !args[1].equals("-pl") && !args[1].equals("-po"))) {
            System.out.println("Usage: java -jar WordTracker.jar <input.txt> -pf|-pl|-po [-f<output.txt>]");
            return;
        }

        String inputFile = args[0];
        String displayMode = args[1];
        String outputFile = (args.length == 3 && args[2].startsWith("-f")) ? args[2].substring(2) : null;

        BSTree<Word> wordTree = loadTree();

        processFile(inputFile, wordTree);

        saveTree(wordTree);

        String output = generateReport(wordTree, displayMode);

        if (outputFile != null) {
            try (PrintWriter writer = new PrintWriter(outputFile)) {
                writer.print(output);
                System.out.println("Output saved to " + outputFile);
            } catch (IOException e) {
                System.err.println("Error writing to file: " + outputFile);
            }
        } else {
            System.out.println(output);
        }
    }

    private static void processFile(String filename, BSTree<Word> tree) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int lineNumber = 1;

            while ((line = reader.readLine()) != null) {
                line = line.replaceAll("[^a-zA-Z0-9\\s]", "").toLowerCase();
                StringTokenizer tokenizer = new StringTokenizer(line);
                Set<String> seenInLine = new HashSet<>();

                while (tokenizer.hasMoreTokens()) {
                    String token = tokenizer.nextToken();

                    if (!seenInLine.add(token)) continue;

                    Word temp = new Word(token);

                    if (tree.contains(temp)) {
                        Word existing = tree.search(temp).getElement();
                        existing.addOccurrence(filename, lineNumber);
                    } else {
                        temp.addOccurrence(filename, lineNumber);
                        tree.add(temp);
                    }
                }

                lineNumber++;
            }
        } catch (IOException e) {
            System.err.println("Error processing file " + filename);
        }
    }

    private static String generateReport(BSTree<Word> tree, String mode) {
        StringBuilder sb = new StringBuilder();
        Iterator<Word> iterator = tree.inorderIterator();

        while (iterator.hasNext()) {
            Word word = iterator.next();
            sb.append(word.getWord()).append(": ");

            word.getOccurrences().forEach((filename, lines) -> {
                sb.append(filename);

                if (mode.equals("-pl") || mode.equals("-po")) {
                    sb.append(" -> found at lines ").append(lines);
                }

                if (mode.equals("-po")) {
                    sb.append(" (x").append(lines.size()).append(")");
                }

                sb.append("; ");
            });

            sb.append("\n");
        }

        return sb.toString();
    }

    @SuppressWarnings("unchecked")
    private static BSTree<Word> loadTree() {
        File file = new File(SERIAL_FILE);
        if (!file.exists()) return new BSTree<>();

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            return (BSTree<Word>) in.readObject();
        } catch (Exception e) {
            System.err.println("Failed to load repository.ser. Starting new tree.");
            e.printStackTrace();
            return new BSTree<>();
        }
    }

    private static void saveTree(BSTree<Word> tree) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(SERIAL_FILE))) {
            out.writeObject(tree);
        } catch (IOException e) {
            System.err.println("Failed to save tree to " + SERIAL_FILE);
            e.printStackTrace();
        }
    }
}
