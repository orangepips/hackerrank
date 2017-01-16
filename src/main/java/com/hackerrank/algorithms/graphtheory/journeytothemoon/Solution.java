package com.hackerrank.algorithms.graphtheory.journeytothemoon;

import java.io.*;
import java.math.BigInteger;
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

        DisjointSet disjointSet = new DisjointSet(N);

        for(int i = 0; i < I; i++){
            int a = scanner.nextInt();
            int b = scanner.nextInt();

            disjointSet.union(a, b);
        }

        BigInteger combinations = binomial(N, 2);

        int parentLenth = disjointSet.parent.length;
        for (int i = 0; i < parentLenth; i++) {
            if (disjointSet.parent[i] != i) continue;
            combinations = combinations.subtract(binomial(disjointSet.sz[i], 2));
        }

        output.append(combinations.toString());
        return output.toString();
    }

    static BigInteger binomial(final int N, final int K) {
        BigInteger ret = BigInteger.ONE;
        for (int k = 0; k < K; k++) {
            ret = ret.multiply(BigInteger.valueOf(N-k))
                    .divide(BigInteger.valueOf(k+1));
        }
        return ret;
    }

    static class DisjointSet
    {
        int[] rank, parent, cache, sz;
        int n;
        boolean done = false;

        // Constructor
        public DisjointSet(int n)
        {
            rank = new int[n];
            parent = new int[n];
            cache = new int[n];
            sz = new int[n];
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
                sz[i] = 1;
            }
        }

        // Returns representative of x's set
        int find(int x) {
            if (done && cache[x] != -1) return cache[x];

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

        int size(int x) {
            return sz[find(x)];
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
            if (sz[xRoot] < sz[yRoot]) {

                // Then move x under y  so that depth
                // of tree remains less
                parent[xRoot] = yRoot;
                sz[yRoot] += sz[xRoot];
            }
            // Else if y's rank is less than x's rank
            else {

                // Then move y under x so that depth of
                // tree remains less
                parent[yRoot] = xRoot;
                sz[xRoot] += sz[yRoot];
            }
        }
    }
}