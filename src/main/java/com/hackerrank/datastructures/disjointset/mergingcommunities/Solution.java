package com.hackerrank.datastructures.disjointset.mergingcommunities;

import java.io.InputStream;
import java.util.*;

public class Solution {
    public static void main(String... args) {
        System.out.println(execute(System.in));
    }

    public static String execute(InputStream inputStream) {
        StringBuffer output = new StringBuffer();
        Scanner scanner = new Scanner(inputStream);

        int n = scanner.nextInt();
        int q = scanner.nextInt();

        DisjointSet disjointSet = new DisjointSet(n + 1);

        for (int k = 0; k < q; k++) {
            String query = scanner.next();
            if (query.equals("Q")) {
                int p = scanner.nextInt();
                output.append(disjointSet.size(p) + (k == q - 1 ? "" : "\n"));
            } else {
                int i = scanner.nextInt();
                int j = scanner.nextInt();
                disjointSet.union(i, j);
            }
        }

        return output.toString();
    }

    // http://www.geeksforgeeks.org/disjoint-set-data-structures-java-implementation/
    // http://p-nand-q.com/python/data-types/general/disjoint-sets.html
    // http://www.geeksforgeeks.org/disjoint-set-data-structures-java-implementation/
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
