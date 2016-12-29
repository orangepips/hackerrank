package dynamicprogramming.sherlockandcost;

import java.util.*;
import static java.lang.Math.abs;
import static java.lang.Math.max;

public class Solution {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int t = scanner.nextInt(); // number of tests
        for (int tests = 0; tests < t; tests++) {
            int n = scanner.nextInt(); // number of values
            int[] b = new int[n];
            for (int i = 0; i < n; i++) {
                b[i] = scanner.nextInt();
            }

            int low = 0;
            int high = 0;
            int l;
            int h;

            System.out.println(0 + ":\t" + b[0] + "\t" + low + "\t" + high);

            for (int i = 1; i < n; i++) {
                int current = b[i];
                int prior = b[i - 1];

                l = max(abs(1 - prior) + high, low);
                h = max(abs(current - 1) + low, abs(current - prior) + high);

                low = l;
                high = h;

                System.out.println(i + ":\t" + b[i] + "\t" + low + "\t" + high);
            }

            System.out.println(max(low, high));
        }
    }
}
