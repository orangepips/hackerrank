package com.hackerrank.datastructures.disjointset.kunduandtree;

import java.io.InputStream;
import java.util.*;

// https://www.hackerrank.com/challenges/kundu-and-tree
public class Solution {
    static final double MAX = Math.pow(10, 9) + 7;
    static final char BLACK = 'b';

    public static void main(String... args) {
        System.out.println(execute(System.in));
    }

    /**
     * Input Format
     * The first line contains an integer N, i.e., the number of vertices in tree.
     * The next N-1 lines represent edges: 2 space separated integers denoting an edge followed by a color of the edge.
     * A color of an edge is denoted by a small letter of English alphabet, and it can be either red(r) or black(b).
     * @param inputStream
     * @return
     */
    public static String execute(InputStream inputStream) {
        StringBuffer output = new StringBuffer();
        Scanner scanner = new Scanner(inputStream);

        int n = scanner.nextInt();
        DisjointSet disjointSet = new DisjointSet(n);
        DisjointSet blackDisjointSet = new DisjointSet(n);

        for (int i = 0; i < n - 1; i++) {
            int start = scanner.nextInt();
            int end = scanner.nextInt();

            disjointSet.union(start - 1, end - 1);

            char c = scanner.next().charAt(0);
            if (c == BLACK) {
                blackDisjointSet.union(start - 1, end - 1);
            }
        }

        int tripletCount = 0;

        for (int first = 1; first <= n; first++) {
            for (int middle = first + 1; middle <= n - 1; middle++) {
                boolean isFirstSegmentAllBlack = (blackDisjointSet.find(first - 1) == blackDisjointSet.find(middle - 1));
                if (isFirstSegmentAllBlack) continue;
                for (int last = n; last > middle; last--) {
                    boolean isSecondSegmentAllBlack = (blackDisjointSet.find(middle - 1) == blackDisjointSet.find(last - 1));
                    boolean isThirdSegmentAllBlack = (blackDisjointSet.find(first - 1) == blackDisjointSet.find(last - 1));
                    if (isThirdSegmentAllBlack || isSecondSegmentAllBlack) continue;
                    tripletCount++;
                }
            }
        }

        if (tripletCount > MAX) {
            tripletCount %= MAX;
        }

        output.append(tripletCount);
        return output.toString();
    }

    // http://www.geeksforgeeks.org/disjoint-set-data-structures-java-implementation/
    static class DisjointSet
    {
        int[] rank, parent;
        int n;

        // Constructor
        public DisjointSet(int n)
        {
            rank = new int[n];
            parent = new int[n];
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
            }
        }

        // Returns representative of x's set
        int find(int x) {
            // Finds the representative of the set
            // that x is an element of
            if (parent[x]!=x)
            {
                // if x is not the parent of itself
                // Then x is not the representative of
                // his set,
                parent[x] = find(parent[x]);

                // so we recursively call Find on its parent
                // and move i's node directly under the
                // representative of this set
            }

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
