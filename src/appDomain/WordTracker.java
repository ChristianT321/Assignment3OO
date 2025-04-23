package appDomain;

import implementations.Word;
import implementations.BSTree;
import utilities.Iterator;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WordTracker {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java WordTracker [-pf|-pl|-po] file1.txt [file2.txt ...]");
            return;
        }

        String flag = args[0];
        List<String> filenames = Arrays.asList(Arrays.copyOfRange(args, 1, args.length));
        BSTree<Word> tree;

        File file = new File("repository.ser");
        if (file.exists()) {
            try {
                ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
                tree = (BSTree<Word>) in.readObject();
                in.close();
            } catch (Exception e) {
                System.out.println("Could not load previous data. Starting with an empty tree.");
                tree = new BSTree<>();
            }
        } else {
            tree = new BSTree<>();
        }

        for (String fileName : filenames) {
            readWordsFromFile(fileName, tree);
        }

        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
            out.writeObject(tree);
            out.close();
        } catch (IOException e) {
            System.out.println("Error saving repository.ser");
        }

        if (flag.equals("-pf")) {
            Iterator<Word> iterator = tree.inorderIterator();
            while (iterator.hasNext()) {
                Word word = iterator.next();
                System.out.println(word.getWord() + " " + getFilenamesOnly(word));
            }
        } else if (flag.equals("-pl")) {
            Iterator<Word> iterator = tree.inorderIterator();
            while (iterator.hasNext()) {
                Word word = iterator.next();
                System.out.println(word.getWord() + " " + word.getLocations());
            }
        } else if (flag.equals("-po")) {
            Iterator<Word> iterator = tree.preorderIterator();
            while (iterator.hasNext()) {
                Word word = iterator.next();
                System.out.println(word.getWord() + " " + word.getLocations() + " [Total: " + word.getTotalFrequency() + "]");
            }
        } else {
            System.out.println("Invalid flag. Use -pf, -pl, or -po.");
        }
    }

    private static void readWordsFromFile(String filename, BSTree<Word> tree) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            int lineNum = 1;

            while ((line = reader.readLine()) != null) {
                String[] words = line.split("[^a-zA-Z]+");

                for (String wordText : words) {
                    if (wordText.isEmpty()) continue;

                    String lowercaseWord = wordText.toLowerCase();
                    Word word = new Word(lowercaseWord);

                    Word existing = null;
                    if (tree.search(word) != null) {
                        existing = tree.search(word).getElement();
                    }

                    if (existing != null) {
                        existing.addLocation(filename, lineNum);
                    } else {
                        word.addLocation(filename, lineNum);
                        tree.add(word);
                    }
                }

                lineNum++;
            }

            reader.close();
        } catch (IOException e) {
            System.out.println("Error reading from file: " + filename);
        }
    }

    private static List<String> getFilenamesOnly(Word word) {
        List<String> filenames = new ArrayList<>();
        for (implementations.WordLocation location : word.getLocations()) {
            filenames.add(location.getFileName());
        }
        return filenames;
    }
}


