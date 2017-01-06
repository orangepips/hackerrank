package com.hackerrank.challenge.stringchains;

import java.io.InputStream;
import java.util.*;

public class Solution {
    public static void main(String... args) {
        System.out.println(execute(System.in));
    }

    public static String execute(InputStream inputStream) {
        StringBuffer output = new StringBuffer();
        Scanner in = new Scanner(inputStream);

        int res;

        int _words_size = 0;
        _words_size = Integer.parseInt(in.nextLine().trim());
        String[] _words = new String[_words_size];
        String _words_item;
        for(int _words_i = 0; _words_i < _words_size; _words_i++) {
            try {
                _words_item = in.nextLine();
            } catch (Exception e) {
                _words_item = null;
            }
            _words[_words_i] = _words_item;
        }

        res = longestChain(_words);

        output.append(res);
        return output.toString();
    }

    /**
     * Delegates the actual work to {@link StringChain} because I found it easier to use some state for each iteration.
     *
     * @param words an array of strings representing the "library" of library to consider.
     * @return the longest chain found relative to the provided {@code library}
     */
    static int longestChain(String[] words) {
        return new StringChain(words).largest;
    }

    /**
     * Finds the largest word "chain" relative to a given library of words.
     */
    static class StringChain {
        /**
         * Count of the largest chain found.
         */
        int largest = 0;

        /**
         * All words passed in a convenient lookup mechanism.
         */
        HashSet<String> library = new HashSet<>();

        /**
         * Memoization of the {@link #countChains(String)} method to provide a tail recursion speedup.
         */
        HashMap<String, Integer> countChainsCache = new HashMap<>();

        /**
         * Constructor. Takes the passed {@code words} and loads into {@link #library}. From there takes each word in
         * {@link #library} and passes to {@link #countChains(String)} to find the largest resulting value and set
         * as {@link #largest}.
         *
         * @param words array of strings constituting the possible library to form chains with.
         */
        StringChain(String[] words) {
            library.addAll(Arrays.asList(words));
            for (String word: library) {
                largest = Math.max(countChains(word), largest);
            }
        }

        /**
         * Recursive function that counts possible entries for each letter removed combination of a passed {@code word}
         * returning the largest value. Returns zero if the word is not in the {@link #library} or 1 if word length is 1.
         *
         * @param word string to test for words chains from by removing a letter
         * @return max count of word chains formed
         */
        private int countChains(String word) {
            if (!library.contains(word)) return 0;
            else if (word.length() == 1) return 1;
            else if (countChainsCache.containsKey(word)) return countChainsCache.get(word);

            HashMap<Integer, Integer> counts = new HashMap<>();
            int n = word.length();
            for (int i = 0; i < n; i++) {
                counts.put(i, 1 + countChains(word.substring(0, i) + word.substring(i + 1)));
            }
            int result = Collections.max(counts.values());
            countChainsCache.put(word, result);
            return result;
        }
    }
}
