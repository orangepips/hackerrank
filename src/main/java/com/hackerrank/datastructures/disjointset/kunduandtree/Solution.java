package com.hackerrank.datastructures.disjointset.kunduandtree;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

// https://www.hackerrank.com/challenges/kundu-and-tree
public class Solution {
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
        BigInteger MAX = BigDecimal.valueOf(Math.pow(10, 9) + 7).toBigInteger();
        char BLACK = 'b';
        int k = 3;

        StringBuffer output = new StringBuffer();
        Scanner scanner = new Scanner(inputStream);

        int n = scanner.nextInt();

        // http://math.stackexchange.com/questions/838792/counting-triplets-with-red-edges-in-each-pair
        DisjointSet verticesSetsWithBlackEdges = new DisjointSet(n + 1);
        ArrayList<int []> segments = new ArrayList<>();
        for (int i = 0; i < n - 1; i++) {
            int start = scanner.nextInt();
            int end = scanner.nextInt();
            char c = scanner.next().charAt(0);

            if (c == BLACK) {
                verticesSetsWithBlackEdges.union(start, end);
            }
        }

        ArrayList<Integer> blackEdgeTreeSizes = new ArrayList<Integer>();
        int parents = verticesSetsWithBlackEdges.parent.length;
        for (int j = 0; j < parents; j++) {
            if (verticesSetsWithBlackEdges.parent[j] != j || verticesSetsWithBlackEdges.sz[j] < 2) continue;
            blackEdgeTreeSizes.add(verticesSetsWithBlackEdges.sz[j]);
        }

        BigInteger tripletCount = binomial(n, k);
        BigInteger badTripletCount = BigInteger.ZERO;
        for (Integer blackEdgeTreeSize: blackEdgeTreeSizes) {
            BigInteger thisBadTripletCount =
            binomial(blackEdgeTreeSize, k).add(
                binomial(blackEdgeTreeSize, k - 1).multiply(
                    BigInteger.valueOf(n - blackEdgeTreeSize)
                )
            );
            badTripletCount = badTripletCount.add(thisBadTripletCount);
        }
        tripletCount = tripletCount.subtract(badTripletCount);

        tripletCount = tripletCount.mod(MAX);
        output.append(tripletCount.toString());
        return output.toString();
    }

    // http://stackoverflow.com/questions/2201113/combinatoric-n-choose-r-in-java-math
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
        int[] parent, cache, sz;
        int n;
        boolean done = false;

        // Constructor
        public DisjointSet(int n)
        {
            parent = new int[n];
            cache = new int[n];
            sz = new int[n];
            this.n = n;
            makeSet();
        }

        @Override
        public String toString() {
            return "DisjointSet{\n" +
                    "\tsz=" + Arrays.toString(sz) +
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
