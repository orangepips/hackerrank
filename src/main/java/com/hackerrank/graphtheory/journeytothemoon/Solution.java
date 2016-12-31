package com.hackerrank.graphtheory.journeytothemoon;

import java.io.*;
import java.util.*;

public class Solution {
    public static void main(String[] args) throws Exception{
        BufferedReader bfr = new BufferedReader(new InputStreamReader(System.in));
        String[] temp = bfr.readLine().split(" ");
        int N = Integer.parseInt(temp[0]);
        int I = Integer.parseInt(temp[1]);

        int[][] matrix = new int[N][N];

        for(int i = 0; i < I; i++){
            temp = bfr.readLine().split(" ");
            int a = Integer.parseInt(temp[0]);
            int b = Integer.parseInt(temp[1]);
            matrix[a][b] = -1;
            matrix[a][a] = -1;
            matrix[b][b] = -1;
        }

        System.out.println(Arrays.deepToString(matrix));

        long combinations = 0;

        for (int row = 0; row < N; row++) {
            for (int col = row + 1; col < N; col++) {
                combinations += (matrix[row][col] == - 1 ? 0 : 1);
            }
        }

        // Compute the final answer - the number of combinations

        System.out.println(combinations);

    }
}