public class OffByOne implements CharacterComparator {
    @Override
    public boolean equalChars(char x, char y) {
        return x - y == 1 || y - x == 1;
    }

    public boolean isPalindrome(String word) {
        if (word.length() <= 1) {
            return true;
        }
        if (!equalChars(word.charAt(0), word.charAt(word.length() - 1))) {
            return false;
        }
        return isPalindrome(word.substring(1, word.length() - 1));
    }
}
