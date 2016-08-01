import java.util.Random;

/**
 * Created by hmly on 6/26/16.
 * Prompt:
 * Pig Latin – Pig Latin is a game of alterations played on the English
 * language game. To create the Pig Latin form of an English word the initial
 * consonant sound is transposed to the end of the word and an ay is affixed
 * (Ex.: "banana" would yield anana-bay).
 * Read Wikipedia for more information on rules.
 */
public class PigLatin {
    public static void main(String[] args) {
        System.out.println(toPigLatin("pig"));
        System.out.println(toPigLatin("banana"));
        System.out.println(toPigLatin("duck"));

        System.out.println(toPigLatin("eat"));
        System.out.println(toPigLatin("omelet"));
        System.out.println(toPigLatin("are"));
        System.out.println(toPigLatin("egg"));
        System.out.println(toPigLatin("apple"));
        System.out.println(toPigLatin("I"));
        System.out.println(toPigLatin("end"));
        System.out.println(toPigLatin("ocelot"));
        System.out.println(toPigLatin("eye"));
    }

    /**
     * All the rules for Pig Latin that involves vowels will
     * select a random rule, with equal chance, and apply it
     * to the original string.
     * @param s the given word
     * @return translated word to Pig Latin
     */
    private static String toPigLatin(String s) {
        Random rand = new Random();
        int n;

        if (s.matches("(?i)^[aeiou].*")) {
            n = rand.nextInt(3);
            if (n == 0)
                return s + "yay"; // most common
            else if (n == 1)
                return s.substring(1) + s.charAt(0) + "way"; // common
            else if (n == 2)
                return s.substring(1) + s.charAt(0) + "i"; // fairly uncommon
            else
                return Lancashire(s);
        } else {
            return s.substring(1) + s.charAt(0) + "ay"; // common
        }
    }

    private static String Lancashire(String s) {
        String str = "";
        String vowels = "aeiou";

        for (int i = 0; i < s.length(); i++) {
            if (vowels.indexOf(s.charAt(i)) > -1)
                s += "ag";
            str += s.charAt(i);
        }
        return str;
    }
}
