import java.util.Scanner;

/**
 * Created by hmly on 6/26/16.
 * Prompt:
 * Count Vowels – Enter a string and the program counts the number of vowels
 * in the text. For added complexity have it report a sum of each vowel found.
 */
public class CountVowels {
    public static void main(String[] args) {
        String str;
        Scanner reader = new Scanner(System.in);

        System.out.print("Enter a str: ");
        str = reader.nextLine();
        System.out.println(countVowels(str));
    }

    private static int countVowels(String str) {
        String vowels = "aeiou";
        int[] counts = new int[5];
        int i, total = 0;
        char ch;

        str = str.toLowerCase();
        for (i = 0; i < str.length(); i++) {
            ch = str.charAt(i);
            if (vowels.indexOf(ch) > -1) {
                total++;
                switch (ch) {
                    case 'a':
                        counts[0]++;
                        break;
                    case 'e':
                        counts[1]++;
                        break;
                    case 'i':
                        counts[2]++;
                        break;
                    case 'o':
                        counts[3]++;
                        break;
                    case 'u':
                        counts[4]++;
                        break;
                    default:
                        break;
                }
            }
        }
        // Display stats for counts
        for (i = 0; i < vowels.length(); i++)
            System.out.println(vowels.charAt(i) + ": " + counts[i]);
        return total;
    }
}
