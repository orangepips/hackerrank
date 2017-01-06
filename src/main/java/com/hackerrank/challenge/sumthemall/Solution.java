package com.hackerrank.challenge.sumthemall;

public class Solution {
    /*public static void main(String... args) {
        System.out.println(execute(System.in));
    }*/

    /*public static String execute(InputStream inputStream) {
        StringBuffer output = new StringBuffer();
        Scanner scanner = new Scanner(inputStream);

        return output.toString();
    }*/

    static int summation(int[] numbers) {
        int sum = 0;
        int len = numbers.length;
        for (int i = 0; i < len; i++) {
            sum += numbers[i];
        }
        return sum;
    }
}
