import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PatternMatching {

    /**
     * Knuth-Morris-Pratt (KMP) algorithm relies on the failure table (also
     * called failure function). Works better with small alphabets.
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for pattern
     * @param comparator you MUST use this to check if characters are equal
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or has
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> kmp(CharSequence pattern, CharSequence text,
                                    CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("The pattern is null or has length 0!");
        }
        if (text == null || comparator == null) {
            throw new IllegalArgumentException("Text or comparator is null!");
        }
        List<Integer> result = new ArrayList<>();
        if (text.length() < pattern.length()) {
            return result;
        }
        int index = 0;
        int patternIndex = 0;
        int[] arr = buildFailureTable(pattern, comparator);
        while (index + pattern.length() <= text.length()) {
            while (patternIndex < pattern.length() && comparator.compare(pattern.charAt(patternIndex),
                    text.charAt(index + patternIndex)) == 0) {
                patternIndex++;
            }
            if (patternIndex == 0) {
                index++;
            } else {
                if (pattern.length() == patternIndex) {
                    result.add(index);
                }
                index += patternIndex - arr[patternIndex - 1];
                patternIndex = arr[patternIndex - 1];
            }
        }
        return result;

    }

    /**
     * Builds failure table that will be used to run the Knuth-Morris-Pratt
     * (KMP) algorithm.
     * @param pattern    a pattern you're building a failure table for
     * @param comparator you MUST use this to check if characters are equal
     * @return integer array holding your failure table
     * @throws java.lang.IllegalArgumentException if the pattern or comparator
     *                                            is null
     */
    public static int[] buildFailureTable(CharSequence pattern,
                                          CharacterComparator comparator) {
        if (pattern == null || comparator == null) {
            throw new IllegalArgumentException("The pattern or comparator is null!");
        }
        if (pattern.length() == 0) {
            return new int[0];
        }
        int i = 0;
        int j = i + 1;
        int[] arr = new int[pattern.length()];
        arr[0] = 0;
        while (j < pattern.length()) {
            if (comparator.compare(pattern.charAt(i), pattern.charAt(j)) == 0) {
                arr[j] = i + 1;
                i++;
                j++;
            } else {
                if (i == 0) {
                    arr[j] = 0;
                    j++;
                } else {
                    i = arr[i - 1];
                }
            }
        }
        return arr;
    }

    /**
     * Boyer Moore algorithm that relies on last occurrence table. Works better
     * with large alphabets.
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for the pattern
     * @param comparator you MUST use this to check if characters are equal
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or has
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> boyerMoore(CharSequence pattern,
                                           CharSequence text,
                                           CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("The pattern is null or has length 0!");
        }
        if (text == null || comparator == null) {
            throw new IllegalArgumentException("Text or comparator is null!");
        }
        List<Integer> result = new ArrayList<>();
        Map<Character, Integer> t = buildLastTable(pattern);
        int i = 0;
        int j;
        while (i <= text.length() - pattern.length()) {
            j = pattern.length() - 1;
            while (j >= 0 && comparator.compare(pattern.charAt(j),
                    text.charAt(i + j)) == 0) {
                j--;
            }
            if (j == -1) {
                result.add(i);
                i++;
            } else {
                char text1 = text.charAt(i + j);
                int shifted = t.getOrDefault(text1, -1);
                if (shifted < j) {
                    i = i + j - shifted;
                } else {
                    i++;
                }
            }
        }
        return result;
    }

    /**
     * Builds last occurrence table that will be used to run the Boyer Moore
     * algorithm.
     * @param pattern a pattern you are building last table for
     * @return a Map with keys of all of the characters in the pattern mapping
     * to their last occurrence in the pattern
     * @throws java.lang.IllegalArgumentException if the pattern is null
     */
    public static Map<Character, Integer> buildLastTable(CharSequence pattern) {
        if (pattern == null) {
            throw new IllegalArgumentException("The pattern is null!");
        }
        Map<Character, Integer> lastTable = new HashMap<>();
        for (int i = 0;  i < pattern.length(); i++) {
            lastTable.put(pattern.charAt(i), i);
        }
        return lastTable;
    }
    
    private static final int BASE = 113;

    /**
     * Runs the Rabin-Karp algorithm. This algorithms generates hashes for the
     * pattern and compares this hash to substrings of the text before doing
     * character by character comparisons.
     * @param pattern    a string you're searching for in a body of text
     * @param text       the body of text where you search for pattern
     * @param comparator you MUST use this to check if characters are equal
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or has
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> rabinKarp(CharSequence pattern,
                                          CharSequence text,
                                          CharacterComparator comparator) {
        int textHash = 0;
        int patternHash = 0;
        int pow = 1;
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("The pattern is null or has length 0!");
        }
        if (text == null || comparator == null) {
            throw new IllegalArgumentException("Text or comparator is null!");
        }
        List<Integer> result = new ArrayList<>();
        if (pattern.length() > text.length()) {
            return result;
        }
        for (int i = pattern.length() - 1; i > -1; i--) {
            textHash += text.charAt(i) * pow;
            patternHash += pattern.charAt(i) * pow;
            if (i != 0) {
                pow *= BASE;
            }
        }
        int i = 0;
        while (text.length() - pattern.length() >= i) {
            if (textHash == patternHash) {
                int j = 0;
                while ((pattern.length() > j) && comparator.compare(text.charAt(i + j),
                        pattern.charAt(j)) == 0) {
                    j++;
                }
                if (j == pattern.length()) {
                    result.add(i);
                }
            }
            if (text.length() - pattern.length() - 1 >= i) {
                textHash = (textHash - text.charAt(i) * pow) * BASE
                        + text.charAt(i + pattern.length());
            }
            i++;
        }
        return result;

    }

    /**
     * The Galil Rule is an addition to Boyer Moore that optimizes how we shift the pattern.
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for the pattern
     * @param comparator you MUST use this to check if characters are equal
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or has
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> boyerMooreGalilRule(CharSequence pattern,
                                          CharSequence text,
                                      CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("The pattern is null or has length 0!");
        }
        if (text == null || comparator == null) {
            throw new IllegalArgumentException("Text or comparator is null!");
        }
        List<Integer> result = new ArrayList<>();
        if (pattern.length() > text.length()) {
            return result;
        }
        Map<Character, Integer> t = buildLastTable(pattern);
        int[] arr = buildFailureTable(pattern, comparator);
        int k = pattern.length() - arr[pattern.length() - 1];
        int i = 0;
        int m = 0;
        while (i <= text.length() - pattern.length()) {
            int j = pattern.length() - 1;
            while (j >= m && comparator.compare(text.charAt(i + j),
                    pattern.charAt(j)) == 0) {
                j--;
            }
            if (j < m) {
                result.add(i);
                m = pattern.length() - k;
                i += k;
            } else {
                m = 0;
                int shifted = t.getOrDefault(text.charAt(i + j), -1);
                if (j > shifted) {
                    i = i + j - shifted;
                } else {
                    i++;
                }
            }
        }
        return result;
    }
}
