/**
 * Created by hmly on 6/26/16.
 * Prompt:
 * Check if Palindrome – Checks if the string entered by the user is a
 * palindrome. That is that it reads the same forwards as backwards like
 * “racecar”
 */
public class Palindrome {
    public static void main(String[] args) {
        System.out.println(isPalindrome("AMANAPLANACANALPANAMA"));
        System.out.println(isPalindrome("MALAYALAM"));
        System.out.println(isPalindrome("RaCeCar"));
        System.out.println(isPalindrome("level"));
        System.out.println(isPalindrome("Civics"));
    }

    private static boolean isPalindrome(String s) {
        int len = s.length();
        s = s.toLowerCase();
        for (int i = 0; i < len/2; i++) {
            if (s.charAt(i) - '0' != s.charAt(len-i-1) - '0')
                return false;
        }
        return true;
    }

    private static boolean isPalindrome2(String s) {
        return new StringBuffer(s).reverse().toString().equals(s);
    }
}
