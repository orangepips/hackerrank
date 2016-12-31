package com.hackerrank.datastructures.trees.balancedforest;

import com.sun.javafx.geom.Edge;

import java.io.InputStream;
import java.util.Scanner;

// https://www.hackerrank.com/challenges/balanced-forest
public class Solution {
    public static void main(String[] args) {
        System.out.println(execute(System.in));
    }

    public static String execute(InputStream inputStream) {
        StringBuffer output = new StringBuffer();
        Scanner scanner = new Scanner(inputStream);
        int treesCount = scanner.nextInt(); // 1 <= q <= 5
        for (int treeIndex = 0; treeIndex < treesCount; treeIndex++) {

        }



        return output.toString();
    }

    /*
    int nodeCount = scanner.nextInt();
        int[] nodeValues = new int[nodeCount];
        for (int i = 0; i < nodeCount; i++) {
            nodeValues[i] = scanner.nextInt();
        }
     */

    // http://www.geeksforgeeks.org/check-if-removing-an-edge-can-divide-a-binary-tree-in-two-halves/

    class Node {
        int value;

    }

    class Edge {

    }
}
