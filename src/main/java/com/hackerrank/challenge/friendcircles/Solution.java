package com.hackerrank.challenge.friendcircles;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Scanner;

public class Solution {
    public static void main(String... args) {
        System.out.println(execute(System.in));
    }

    public static String execute(InputStream inputStream) {
        StringBuffer output = new StringBuffer();
        Scanner in = new Scanner(inputStream);

        int res;

        int _friends_size = 0;
        _friends_size = Integer.parseInt(in.nextLine().trim());
        String[] _friends = new String[_friends_size];
        String _friends_item;
        for(int _friends_i = 0; _friends_i < _friends_size; _friends_i++) {
            try {
                _friends_item = in.nextLine();
            } catch (Exception e) {
                _friends_item = null;
            }
            _friends[_friends_i] = _friends_item;
        }

        res = friendCircles(_friends);
        output.append(res);

        return output.toString();
    }

    /**
     * Uses a disjoint set abstract data type to treat the problem as a set of nodes and edges. People being the nodes
     * and edges being friendship. The {@link DisjointSet#parent} array uses path compression to facilitate determining
     * what group a particular person is a member of. In this particular case we are only interested in finding the
     * distinct groups, which can be determined by finding those nodes whose parents are themselves - typically referred
     * to as the "representative" node in a disjoint set for a given set therein.
     *
     * I have noted below where the disjoint set code was taken from, which is contrary to the test instructions, but
     * it seems silly to simply rewrite instead. I can certainly speak to how this works if necessary.
     *
     * @param friends array of strings containing a combination of Y or N
     * @return count of the number of friend circles - i.e. connected groups
     */
    static int friendCircles(String[] friends) {
        int n = friends.length;
        DisjointSet disjointSet = new DisjointSet(n);
        for (int i = 0; i < n; i++) {
            String line = friends[i];
            for (int j = 0; j < n; j++) {
                char c = line.charAt(j);
                if (c == 'N') continue;
                disjointSet.union(i, j);
            }
        }
        int count = 0;
        for (int k = 0; k < n; k++) {
            count += (disjointSet.parent[k] == k ? 1 : 0);
        }
        return count;
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
