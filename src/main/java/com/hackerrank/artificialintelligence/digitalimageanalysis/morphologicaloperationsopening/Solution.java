package com.hackerrank.artificialintelligence.digitalimageanalysis.morphologicaloperationsopening;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

// https://www.hackerrank.com/challenges/dip-morphological-operations-closing
// http://homepages.inf.ed.ac.uk/rbf/HIPR2/morops.htm
public class Solution {
    public static void main(String... args) {
        System.out.println(execute(System.in));
    }

    static int[][] B = new int[][] {
        string2ints("0000000000"),
        string2ints("0111111100"),
        string2ints("0000111100"),
        string2ints("0000111100"),
        string2ints("0001111100"),
        string2ints("0000111100"),
        string2ints("0001100000"),
        string2ints("0000000000"),
        string2ints("0000000000")
    };

    static List<Position> S = new ArrayList<Position>();
    static {
        S.addAll(Arrays.asList(
            new Position(-1, -1),
            new Position(0, -1),
            new Position(1, -1),
            new Position(-1, 0),
            new Position(0, 0),
            new Position(1, 0),
            new Position(-1, 1),
            new Position(0, 1),
            new Position(1, 1)
        ));
    }

    static class Position {
        int x;
        int y;
        Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return  x + ", " + y;
        }
    }

    public static String execute(InputStream inputStream) {
        StringBuffer output = new StringBuffer();
        Scanner scanner = new Scanner(inputStream);

        //System.out.println(array2d2String(B));
        int[][] result = open(B);
        //System.out.println(array2d2String(result));
        int count = 0;
        for (int[] line: result) {
            for (int i: line) {
                count += (i == 1 ? 1 : 0);
            }
        }
        output.append(count);
        return output.toString();
    }

    static int[][] close(int[][] B) {
        return(erode(dialate(B)));
    }

    static int[][] dialate(int[][] B) {
        int xOffset = 1, yOffset = 1;
        int result[][] = padEdges(B, xOffset, yOffset);

        int xL = B.length, yL = B[0].length;
        for (int x = 0; x < xL; x++) {
            for (int y = 0; y < yL; y++) {
                if (B[x][y] == 0) continue;
                int rX = x+xOffset, rY = y+yOffset;
                for (Position position: S) {
                    result[rX + position.x][rY + position.y] = 1;
                }
            }
        }

        return result;
    }

    static int[][] erode(int[][] B) {
        int result[][] = new int[B.length][B[0].length];
        int rL = result.length;
        for (int i = 0; i < rL; i++) {
            result[i] = Arrays.copyOf(B[i], rL);
        }
        int bX = B.length, bY = B[0].length;
        for (int x = 0; x < bX; x++) {
            for (int y = 0; y < bY; y++) {
                if (B[x][y] == 0) continue;
                int oneCount = 0;
                for (Position position: S) {
                    oneCount += B[x + position.x][y + position.y];
                }
                if (oneCount == 9) continue;
                result[x][y] = 0;
            }
        }
        return result;
    }

    static int[][] open(int[][] B) {
        return(dialate(erode(B)));
    }

    private static int[][] padEdges(int[][] B, int x, int y) {
        int result[][] = new int[x*2 + B.length][y*2 + B[0].length];
        for (int sX = 0, dX = x; sX < B.length; sX++, dX++) {
            for (int sY = 0, dY = x; sY < B[0].length; sY++, dY++) {
                result[dX][dY] = B[sX][sY];
            }
        }
        return result;
    }

    private static int[] string2ints(String string) {
        int n = string.length();
        int[] ints = new int[n];
        for (int i = 0; i <= n - 1; i++) {
            ints[i] = Character.getNumericValue(string.charAt(i));
        }
        return ints;
    }

    private static String array2d2String(int[][] A) {
        StringBuffer sb = new StringBuffer();
        int xL = A.length;
        for (int x = 0; x < xL; x++) {
            sb.append(Arrays.toString(A[x]).replace("[", "").replace("]", "").replace(", ", "") + (x == xL - 1 ? "" : "\n"));
        }
        return sb.toString();
    }
}
