package com.hackerrank.challenge.staircase;

public class Solution {
    /*public static void main(String... args) {
        System.out.println(execute(System.in));
    }*/

    /*public static String execute(InputStream inputStream) {
        StringBuffer output = new StringBuffer();
        Scanner scanner = new Scanner(inputStream);

        return output.toString();
    }*/

    static void StairCase(int n) {
        for (int i = 1; i <= n; i++) {
            String spaces = repeat(" ", n-i);
            String pounds = repeat("#", i);
            System.out.println(spaces + pounds);
        }
    }

    static String repeat(String c, int x) {
        return new String(new char[x]).replace("\0", c);
    }
}
