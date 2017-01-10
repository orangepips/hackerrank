package com.hackerrank.algorithms.graphtheory.journeytothemoon;

import java.io.*;
import java.util.*;

// https://www.hackerrank.com/challenges/journey-to-the-moon
public class Solution {
    public static void main(String... args) {
        System.out.println(execute(System.in));
    }

    public static String execute(InputStream inputStream) {
        StringBuffer output = new StringBuffer();
        Scanner scanner = new Scanner(inputStream);
        int N = scanner.nextInt();
        int I = scanner.nextInt();

        Graph graph = new Graph(N);

        for(int i = 0; i < I; i++){
            int[] ids = new int[2];
            ids[0] = scanner.nextInt();
            ids[1] = scanner.nextInt();

            Arrays.sort(ids);
            graph.nodes.get(ids[0]).hasEdge.set(ids[1], Boolean.FALSE);
        }

        int combinations = 0;
        for (int nodeIdx = 0; nodeIdx < N - 1; nodeIdx++) {
            int edgeIdx = nodeIdx + 1;
            Graph.Node node = graph.nodes.get(nodeIdx);
            List<Boolean> hasEdges = node.hasEdge.subList(edgeIdx, N);
            // System.out.println(edgeIdx + " " + node + " " + hasEdges);
            for (Boolean hasEdge: hasEdges) {
                combinations += hasEdge ? 1 : 0;
            }
        }
        output.append(combinations);
        return output.toString();
    }



    static class Graph {
        int n;
        List<Node> nodes;

        Graph(int n) {
            this.n = n;
            nodes = new ArrayList<>(n);
            for (int i = 0; i < n; i++) {
                nodes.add(new Node(i));
            }
        }

        class Node {
            int id;
            List<Boolean> hasEdge;

            Node (int id) {
                this.id = id;
                this.hasEdge = new ArrayList<>(n);
                for (int i = 0; i < n; i++) {
                    hasEdge.add(!(i==id));
                }
            }

            @Override
            public String toString() {
                return "Node{" +
                        "id=" + id +
                        ", hasEdge=" + hasEdge +
                        '}';
            }
        }
    }
}