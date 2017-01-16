package com.hackerrank.algorithms.dynamicprogramming.maxsubarray;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Solution {
    public static void main(String... args) {
        System.out.println(execute(System.in));
    }

    public static String execute(InputStream inputStream) {
        StringBuffer output = new StringBuffer();
        Scanner scanner = new Scanner(inputStream);

        int T = scanner.nextInt();
        for (int i = 0; i < T; i++) {
            int N = scanner.nextInt();

            int[] values = new int[N];

            for (int j = 0; j < N; j++) {
                values[j] = scanner.nextInt();
            }
            // System.out.println(T + ":\t" + Arrays.toString(values));

//            http://www.programcreek.com/2013/02/leetcode-maximum-subarray-java/
            int max_non_contiguous = values[0];
            int max_ending_here = values[0], max_so_far = values[0];
            for (int x = 1; x < N; x++) {
                int value = values[x];
                if (max_non_contiguous < 0) {
                    max_non_contiguous = Math.max(max_non_contiguous, value);
                } else if (value > 0){
                    max_non_contiguous += value;
                }
                max_ending_here = Math.max(max_ending_here + value, value);
                max_so_far = Math.max(max_so_far, max_ending_here);
            }
            output.append(max_so_far + " " + max_non_contiguous + "\n");
        }

        return output.toString().trim();
    }
}
