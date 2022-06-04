public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> d = new LinkedListDeque<>();
        for (int i = 0; i < word.length(); i++) {
            d.addLast(word.charAt(i));
        }
        return d;
    }

    public boolean isPalindrome(String word) {
        if (word.length() <= 1) {
            return true;
        }
        Deque<Character> d = wordToDeque(word);
        if (d.removeFirst() != d.removeLast()) {
            return false;
        }
        return isPalindrome(word.substring(1, word.length() - 1));
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        if (word.length() <= 1) {
            return true;
        }
        if (!cc.equalChars(word.charAt(0), word.charAt(word.length() - 1))) {
            return false;
        }
        return isPalindrome(word.substring(1, word.length() - 1), cc);
    }

}
