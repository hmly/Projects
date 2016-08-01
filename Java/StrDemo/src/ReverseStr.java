/**
 * Created by hmly on 6/26/16.
 * Prompt:
 * Reverse a String – Enter a string and the program will reverse it and print it out.
 */
public class ReverseStr {
    public static void main(String[] args) {
        String str = "The quick brown fox jumps over the lazy dog";
        System.out.println(reverse(str));
        System.out.println(reverse2(str));
    }

    // Without using library functions
    private static String reverse(String s) {
        String str = "";
        for (int i = s.length()-1; i >= 0; i--)
            str = str.concat(String.valueOf(s.charAt(i)));
        return str;
    }

    // Using library functions
    private static String reverse2(String s) {
        return new StringBuilder(s).reverse().toString();
    }
}