package com.hackerrank.algorithms.sorting.countingsort4;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.TreeSet;

public class Solution {
    public static void main(String... args) {
        System.out.println(execute(System.in));
    }

    public static String execute(InputStream inputStream) {
        StringBuffer output = new StringBuffer();
        Scanner scanner = new Scanner(inputStream);

        int n = scanner.nextInt();
        int firstHalf = n / 2;

//        TreeSet<StableSortElement> elements = new TreeSet<StableSortElement>();
        ArrayList<StableSortElement> elements = new ArrayList<>();

        for (int order = 0; order < n; order++) {
            int group = scanner.nextInt();
            String text = scanner.next();
            elements.add(new StableSortElement(order, group, text, (order < firstHalf)));
        }

        Collections.sort(elements);
        for(StableSortElement element: elements) {
            output.append(element.toString() + " ");
        }

        return output.toString().trim();
    }

    static class StableSortElement implements Comparable<StableSortElement> {
        int group;
        int order;
        String text;
        boolean isFirstHalf;

        StableSortElement(int order, int group, String text, boolean isFirstHalf) {
            this.order = order;
            this.group = group;
            this.text = text;
            this.isFirstHalf = isFirstHalf;
        }

        public int compareTo(StableSortElement o) {
            int groupCompareTo = new Integer(this.group).compareTo(new Integer(o.group));
            if (groupCompareTo != 0) return groupCompareTo;
            else return new Integer(this.order).compareTo(new Integer(o.order));
        }

        @Override
        public String toString() {
            return isFirstHalf ? "-" : text;
        }
    }
}
