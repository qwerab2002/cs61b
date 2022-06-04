import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testIsPalindrome() {
        assertFalse(palindrome.isPalindrome("cat"));
        assertFalse(palindrome.isPalindrome("delivered"));
        assertTrue(palindrome.isPalindrome("racecar"));
        assertTrue(palindrome.isPalindrome("r"));
        assertTrue(palindrome.isPalindrome(""));
    }

    @Test
    public void testIsPalindromeOBO() {
        CharacterComparator offByOne = new OffByOne();
        assertFalse(palindrome.isPalindrome("cat", offByOne));
        assertFalse(palindrome.isPalindrome("racecar", offByOne));
        assertTrue(palindrome.isPalindrome("detrude", offByOne));
        assertTrue(palindrome.isPalindrome("flake", offByOne));
        assertTrue(palindrome.isPalindrome("r", offByOne));
        assertTrue(palindrome.isPalindrome("", offByOne));
    }
    @Test
    public void testIsPalindromeOBN() {
        CharacterComparator ob5 = new OffByN(5);
        assertTrue(palindrome.isPalindrome("binding", ob5));
        assertFalse(palindrome.isPalindrome("racecar", ob5));
    }
}
