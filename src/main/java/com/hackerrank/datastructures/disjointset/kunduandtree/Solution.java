package com.hackerrank.datastructures.disjointset.kunduandtree;

import java.io.InputStream;
import java.util.*;

// https://www.hackerrank.com/challenges/kundu-and-tree
public class Solution {
    static final int MAX = 10^9 + 7;

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
        Tree tree = new Tree(n);

        for (int i = 0; i < n - 1; i++) {
            int start = scanner.nextInt();
            int end = scanner.nextInt();
            char c = scanner.next().charAt(0);
            tree.addColoredSegment(start, end, c);
        }
        tree.process();

        int answer = tree.triplets.size();
        // If the answer is greater than 10^9 + 7, print the answer modulo (%) 10^9 + 7.
        if (answer > MAX) {
            answer %= MAX;
        }
        output.append(answer);

        return output.toString();
    }

    static class Triplet {
        TreeSet<Integer> sortedVerticies = new TreeSet<>();

        Triplet(int... vertices) {
            for (int vertex: vertices) {
                sortedVerticies.add(vertex);
            }
        }

        @Override
        public String toString() {
            StringBuffer sb = new StringBuffer();
            for (Integer vertex: sortedVerticies) {
                sb.append(vertex + " ");
            }
            return sb.toString().trim();
        }
    }

    static class Tree {
        HashSet<Triplet> triplets = new HashSet<>();
        private TreeMap<Integer, TreeSet<ColoredSegment>> startsWithColoredSegments = new TreeMap<>();
        private TreeMap<Integer, TreeSet<Segment>> redContainingSegments = new TreeMap<>();

        Tree(int vertices) {
            for (int vertex = 1; vertex <= vertices; vertex++) {
                startsWithColoredSegments.put(vertex, new TreeSet<ColoredSegment>());
                redContainingSegments.put(vertex, new TreeSet<Segment>());
            }
        }

        @Override
        public String toString() {
            return "Tree{" +
                    "triplets=" + triplets +
                    ", startsWithColoredSegments=" + startsWithColoredSegments +
                    ", redContainingSegments=" + redContainingSegments +
                    '}';
        }

        void addColoredSegment(int start, int end, char c) {
            ColoredSegment coloredSegment = new ColoredSegment(start, end, c);
            startsWithColoredSegments.get(start).add(coloredSegment);
        }


        void process() {
            for (Integer startIndex: startsWithColoredSegments.keySet()) {
                for (ColoredSegment startColoredSegment : startsWithColoredSegments.get(startIndex)) {
                    followPath(startColoredSegment, startColoredSegment, false);
                }
            }
            for (Integer startIndex: redContainingSegments.keySet()) {
                for (Segment startSegment: redContainingSegments.get(startIndex)) {
                    for (Segment endSegment: redContainingSegments.get(startSegment.end)) {
                        triplets.add(new Triplet(startSegment.start, startSegment.end, endSegment.end));
                    }
                }
            }
        }

        private void followPath(ColoredSegment startingColoredSegment, ColoredSegment currentColoredSegment, boolean hasRedPart) {
            hasRedPart = (hasRedPart || currentColoredSegment.color.equals(ColoredSegment.Color.RED));
            if (hasRedPart) {
                redContainingSegments.get(startingColoredSegment.start).add(
                    new Segment(startingColoredSegment.start, currentColoredSegment.end)
                );
            }

            TreeSet<ColoredSegment> nextColoredSegments = startsWithColoredSegments.get(currentColoredSegment.end);
            for (ColoredSegment nextColoredSegment: nextColoredSegments) {
                followPath(startingColoredSegment, nextColoredSegment, hasRedPart);
            }
        }
    }

    static class ColoredSegment extends Segment {
        Color color;

        ColoredSegment(int start, int end, char c) {
            super(start, end);
            this.color = Color.BY_LETTER.get(c);
        }

        @Override
        public String toString() {
            return "Segment{" +
                    "start=" + start +
                    ", end=" + end +
                    ", color=" + color +
                    '}';
        }

        public enum Color {
            RED('r'),
            BLACK('b');

            static final HashMap<Character, Color> BY_LETTER = new HashMap<>();
            static {
                for (Color color: Color.values()) {
                    BY_LETTER.put(color.c, color);
                }
            }

            char c;

            Color(char c) {
                this.c = c;
            }
        }
    }

    static class Segment implements Comparable<Segment> {
        int start;
        int end;

        Segment(int start, int end) {
            this.start = start;
            this.end = end;
        }

        /**
         * Look at start and then end
         * @param o
         * @return
         */
        @Override
        public int compareTo(Segment o) {
            if (equals(o)) return 0;
            int startCompareTo = new Integer(start).compareTo(o.start);
            if (startCompareTo != 0) return startCompareTo;
            return new Integer(end).compareTo(o.end);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Segment that = (Segment) o;
            return start == that.start &&
                    end == that.end;
        }

        @Override
        public int hashCode() {
            return Objects.hash(start, end);
        }

        @Override
        public String toString() {
            return "Segment{" +
                    "start=" + start +
                    ", end=" + end +
                    '}';
        }
    }
}
