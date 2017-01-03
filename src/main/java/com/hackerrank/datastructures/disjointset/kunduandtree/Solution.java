package com.hackerrank.datastructures.disjointset.kunduandtree;

import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

// https://www.hackerrank.com/challenges/kundu-and-tree
public class Solution {
    static final BigInteger MAX = BigDecimal.valueOf(Math.pow(10, 9) + 7).toBigInteger();
    static final char BLACK = 'b';
    static final char RED = 'r';

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

        int k = 3;
        int n = scanner.nextInt();

        DisjointSet blackEdgeVerticesDisjointSet = new DisjointSet(n);
        DisjointSet redEdgeVerticesDisjointSet = new DisjointSet(n);
        TreeSet<Integer> verticesWithRedEdges = new TreeSet<>();
        for (int i = 0; i < n - 1; i++) {
            int start = scanner.nextInt() - 1;
            int end = scanner.nextInt() - 1;

            char c = scanner.next().charAt(0);

            if (c == BLACK) {
                blackEdgeVerticesDisjointSet.union(start, end);
            } else {
                redEdgeVerticesDisjointSet.union(start, end);
                verticesWithRedEdges.add(start);
                verticesWithRedEdges.add(end);
            }
        }
        blackEdgeVerticesDisjointSet.done = true;
        redEdgeVerticesDisjointSet.done = true;
        System.out.println(blackEdgeVerticesDisjointSet);
        System.out.println(redEdgeVerticesDisjointSet);
        System.out.println(verticesWithRedEdges);

        BigInteger candidates = binomial(n, k);

        HashMap<Integer, TreeSet<Integer>> connectedVerticesWithBlackEdges = new HashMap<>();
        for (int i = 0; i < n; i++) {
            int blackEdgeVertexRepresentative = blackEdgeVerticesDisjointSet.find(i);
            int redEdgeVertexRepresentative = redEdgeVerticesDisjointSet.find(i);
            if (connectedVerticesWithBlackEdges.get(blackEdgeVertexRepresentative) == null) {
                connectedVerticesWithBlackEdges.put(blackEdgeVertexRepresentative, new TreeSet<Integer>());
            }
            connectedVerticesWithBlackEdges.get(blackEdgeVertexRepresentative).add(i);
        }

        System.out.println(connectedVerticesWithBlackEdges);

        for (Integer representative: connectedVerticesWithBlackEdges.keySet()) {
            TreeSet<Integer> vertices = connectedVerticesWithBlackEdges.get(representative);
            vertices.removeAll(verticesWithRedEdges);
            //System.out.println(vertices.size() + " " + k);
            //System.out.println(binomial(vertices.size(), k));
            candidates = candidates.subtract(binomial(vertices.size(), k));
        }

        output.append(candidates.toString());
        if (candidates.compareTo(MAX) == 1) {
            candidates = candidates.mod(MAX);
        }
        output.append(candidates.toString());

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

    static class InValidTripletCounter {
        private final int K;
        private final int n;

        private BigInteger inValidTripletCount = BigInteger.ZERO;
        //private DisjointSet disjointSet;
        private DisjointSet blackDisjointSet;

        InValidTripletCounter(Scanner scanner, int k) {
            K = k;
            this.n = scanner.nextInt();

            blackDisjointSet = new DisjointSet(this.n);

            for (int i = 0; i < n - 1; i++) {
                int start = scanner.nextInt();
                int end = scanner.nextInt();

                char c = scanner.next().charAt(0);
                if (c == BLACK) {
                    blackDisjointSet.union(start - 1, end - 1);
                }
            }
            blackDisjointSet.done = true;

//            count(n);

            HashMap<Integer, TreeSet<Integer>> allBlackEdges = new HashMap<>();
            for (int i = 0; i < n; i++) {
                int representative = blackDisjointSet.find(i);
                if (allBlackEdges.get(representative) == null) {
                    allBlackEdges.put(representative, new TreeSet<Integer>());
                }
                allBlackEdges.get(representative).add(i);
            }

            for (Integer representative: allBlackEdges.keySet()) {
                TreeSet<Integer> vertices = allBlackEdges.get(representative);
                inValidTripletCount.add(binomial(vertices.size(), k));
            }
        }

        BigInteger getCount() {
            return inValidTripletCount;
        }

        // http://stackoverflow.com/questions/29910312/algorithm-to-get-all-the-combinations-of-size-n-from-an-array-java
//        private void count(int n) {
//            int[] input = new int[n];
//            for (int i = 1; i <= n; i++) {
//                input[i-1] = i;
//            }
//            int[] s = new int[K];                  // here we'll keep indices
//            if (K <= input.length) {
//                // first index sequence: 0, 1, 2, ...
//                for (int i = 0; (s[i] = i) < K - 1; i++);
//                //subsets.add(getSubset(input, s));
//                inValidTripletCount += (isValidSubset(getSubset(input, s)) ? 1 : 0);
//                for(;;) {
//                    int i;
//                    // find position of item that can be incremented
//                    for (i = K - 1; i >= 0 && s[i] == input.length - K + i; i--);
//                    if (i < 0) {
//                        break;
//                    } else {
//                        s[i]++;                    // increment this item
//                        for (++i; i < K; i++) {    // fill up remaining items
//                            s[i] = s[i - 1] + 1;
//                        }
//                        //subsets.add(getSubset(input, s));
//                        inValidTripletCount += (isValidSubset(getSubset(input, s)) ? 1 : 0);
//                    }
//                }
//            }
//        }
//
//        // generate actual subset by index sequence
//        private int[] getSubset(int[] input, int[] subset) {
//            int[] result = new int[subset.length];
//            for (int i = 0; i < subset.length; i++)
//                result[i] = input[subset[i]];
//            return result;
//        }
//
//        private boolean isValidSubset(int[] subset) {
//            int first = subset[0] - 1;
//            int middle = subset[1] - 1;
//            int last = subset[2] - 1;
//            if (blackDisjointSet.find(first) == blackDisjointSet.find(middle)) return false;
//            else if (blackDisjointSet.find(middle) == blackDisjointSet.find(last)) return false;
//            else if (blackDisjointSet.find(last) == blackDisjointSet.find(middle)) return false;
//            else return true;
//        }
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
