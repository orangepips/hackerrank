package com.hackerrank.datastructures.disjointset.componentsinagraph;

import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class Solution {
    public static void main(String... args) {
        System.out.println(execute(System.in));
    }

    public static String execute(InputStream inputStream) {
        StringBuffer output = new StringBuffer();
        Scanner scanner = new Scanner(inputStream);

        int n = scanner.nextInt();
        int maxVertextId = 2 * n + 1;

        DisjointSet disjointSet = new DisjointSet(maxVertextId);

        for (int i = 0; i < n; i++) {
            int g = scanner.nextInt();
            int b = scanner.nextInt();

            disjointSet.union(g, b);
        }
        disjointSet.done = true;

        HashMap<Integer, HashSet<Integer>> graphs = new HashMap<>();
        for (int j = 1; j < maxVertextId; j++) {
            int representative = disjointSet.find(j);
            if (graphs.get(representative) == null) graphs.put(representative, new HashSet<Integer>());
            graphs.get(representative).add(j);
        }

        int smallest = Integer.MAX_VALUE;
        int largest = Integer.MIN_VALUE;
        for (int representative: graphs.keySet()) {
            int vertexCount = graphs.get(representative).size();
            if (vertexCount < 2) continue;
            smallest = Math.min(vertexCount, smallest);
            largest = Math.max(vertexCount, largest);
        }

        output.append(smallest + " " + largest);

        return output.toString();
    }

    // http://www.geeksforgeeks.org/disjoint-set-data-structures-java-implementation/
    static class DisjointSet
    {
        int[] rank, parent, cache;
        int n;
        boolean done = false;

        // Constructor
        public DisjointSet(int n)
        {
            rank = new int[n];
            parent = new int[n];
            cache = new int[n];
            this.n = n;
            makeSet();
        }

        @Override
        public String toString() {
            return "DisjointSet{\n" +
                    "\trank=" + Arrays.toString(rank) +
                    ",\n\tparent=" + Arrays.toString(parent) +
                    ",\n\tn=" + n +
                    '}';
        }

        // Creates n sets with single item in each
        void makeSet()
        {
            for (int i=0; i<n; i++)
            {
                // Initially, all elements are in
                // their own set.
                parent[i] = i;
                cache[i] = -1;
            }
        }

        // Returns representative of x's set
        int find(int x) {
            if (done && cache[x] != -1) {
                return cache[x];
            }
            // Finds the representative of the set
            // that x is an element of
            if (parent[x]!=x)
            {
                // if x is not the parent of itself
                // Then x is not the representative of
                // this set,
                parent[x] = find(parent[x]);

                // so we recursively call Find on its parent
                // and move i's node directly under the
                // representative of this set
            }
            if (done) cache[x] = parent[x];
            return parent[x];
        }

        // Unites the set that includes x and the set
        // that includes x
        void union(int x, int y) {
            // Find representatives of two sets
            int xRoot = find(x), yRoot = find(y);

            // Elements are in the same set, no need
            // to unite anything.
            if (xRoot == yRoot)
                return;

            // If x's rank is less than y's rank
            if (rank[xRoot] < rank[yRoot])

                // Then move x under y  so that depth
                // of tree remains less
                parent[xRoot] = yRoot;

                // Else if y's rank is less than x's rank
            else if (rank[yRoot] < rank[xRoot])

                // Then move y under x so that depth of
                // tree remains less
                parent[yRoot] = xRoot;

            else // if ranks are the same
            {
                // Then move y under x (doesn't matter
                // which one goes where)
                parent[yRoot] = xRoot;

                // And increment the the result tree's
                // rank by 1
                rank[xRoot] = rank[xRoot] + 1;
            }
        }
    }
}
