import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

/**
 * Created by hmly on 6/27/16.
 * Prompt:
 * Count Words in a String – Counts the number of individual words in a string.
 * For added complexity read these strings in from a text file and generate a
 * summary (includes char, word, and line counts).
 */
public class CountWords {
    public static void main(String[] args) {
        Path path = Paths.get("src/Text/Sample.txt");
        int wcount=0, chrcount=0, linecount=0;

        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                chrcount += countChars(line);
                wcount += countWords(line);
                linecount++;
            }

            // Display summary
            System.out.println("char count: " + chrcount);
            System.out.println("word count: " + wcount);
            System.out.println("line count: " + linecount);
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
    }

    private static int countChars(String str) {
        int count = 0;
        for (String s : str.split("\\s"))
            count += s.length();
        return count;
    }

    private static int countWords(String str) {
        return str.split("\\s").length;
    }
}
