package org.khanacademy;


import java.util.Arrays;

public class Sorting {
    public static void main(String... args) {
        int[] a = new int[] {14, 7, 3, 12, 9, 11, 6, 2, 1};
        System.out.println(Arrays.toString(mergeSort(a)));

        int[] b = new int[] {12, 7, 88, 14, 9, 10, 11};
        System.out.println(Arrays.toString(quickSort(b)));
    }

    /**
     * https://www.khanacademy.org/computing/computer-science/algorithms/quick-sort/a/overview-of-quicksort
     * average run time: n log n / worst case n^2 when partition is largest or smallest element
     *   can generally overcome worst case typically by randomly choosing
     *   multiple elements (3 or more) and choosing the median as the pivot
     * space: n
     * not stable
     * @param a array to sort
     */
    static int[] quickSort(int[] a) {
        return partition(a, 0, a.length);
    }

    private static int[] partition(int a[], int start, int end) {
        if (end - start <= 2) return a;
        int r = end - 1;
        int pivot = a[r];
        int q = start;

        // partition
        for (int j = start; j < end; j++) {
            if (pivot <= a[j] && r != j) continue;
            int tmp = a[j];
            a[j] = a[q];
            if (r == j) {
                a[q] = tmp;
            } else {
                q++;
                a[q - 1] = tmp;
            }
        }

        partition(a, start, q - 1);
        partition(a, q, end);

        return a;
    }

    // https://www.khanacademy.org/computing/computer-science/algorithms/merge-sort/a/overview-of-merge-sort
    // stable sort
    // average run time: n log n
    // space: 2n
    static int[] mergeSort(int[] a) {
        int[] x = Arrays.copyOfRange(a, 0, a.length /2 );
        int[] y = Arrays.copyOfRange(a, a.length / 2, a.length);
//        System.out.println("x:\t" + Arrays.toString(x));
//        System.out.println("y:\t" + Arrays.toString(y));
        x = x.length > 1 ? mergeSort(x) : x;
        y = y.length > 1 ? mergeSort(y) : y;

        int xL = x.length, yL = y.length;
        int[] c = new int[xL + yL];
        for (int xIdx = 0, yIdx = 0; xIdx + yIdx < xL + yL; ) {
            int pos = xIdx + yIdx;

            if (xIdx >= xL) {
                c[pos] = y[yIdx];
                yIdx++;
                continue;
            }
            if (yIdx >= yL) {
                c[pos] = x[xIdx];
                xIdx++;
                continue;
            }

            int xVal = x[xIdx];
            int yVal = y[yIdx];

            c[pos] = xVal < yVal ? xVal : yVal;
            if (xVal < yVal) xIdx++;
            else yIdx++;
        }
//        System.out.println("c:\t" + Arrays.toString(c));
        return c;
    }
}
