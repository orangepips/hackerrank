package datastructures.trie.noprefixset;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

// https://www.hackerrank.com/challenges/no-prefix-set
public class Solution {
    public static void main(String... args) {
        System.out.println(execute(System.in));
    }

    public static String execute(InputStream inputStream) {
        StringBuffer output = new StringBuffer();
        Scanner scanner = new Scanner(inputStream);
        TrieNode rootTrieNode = new TrieNode();
        int n = scanner.nextInt();
        scanner.nextLine();
        ArrayList<String> words = new ArrayList<String>();
        introduce:
        for (int i = 0; i < n; i++) {
            String word = scanner.nextLine();
            if (!rootTrieNode.add(word)) {
                output.append("BAD SET\n");
                output.append(word);
                break introduce;
            }
        }
        if (output.length() == 0) output.append("GOOD SET");
        return output.toString();
    }

    // https://github.com/acbellini/juzzle/blob/master/TrieDictionary/src/net/webeggs/juzzle/vocabulary/LowercaseTrieVocabulary.java
    static class TrieNode {
        static final String VOCABULARY = "abcdefghij";

        private boolean isWord = false;

        int numChildren = 0;

        TrieNode[] children = new TrieNode[VOCABULARY.length()];

        boolean add(String s) {
            char first = s.charAt(0);
            int index = VOCABULARY.indexOf(first);
            TrieNode child = children[index];
            if (child == null) {
                child = new TrieNode();
                children[index] = child;
                numChildren++;
            }

            if (child.isWord) {
                return false;
            }

            if (s.length() == 1) {
                if (child.isWord) {
                    // The word is already in the trie
                    return false;
                }
                child.isWord = true;
                return child.numChildren == 0;
            } else {
                // Recurse into sub-trie
                return child.add(s.substring(1));
            }
        }

        public TrieNode getNode(String s) {
            TrieNode node = this;
            for (int i = 0; i < s.length(); i++) {
                int index = VOCABULARY.indexOf(s.charAt(i));
                TrieNode child = node.children[index];
                if (child == null) {
                    // There is no such word
                    return null;
                }
                node = child;
            }
            return node;
        }
    }
}
