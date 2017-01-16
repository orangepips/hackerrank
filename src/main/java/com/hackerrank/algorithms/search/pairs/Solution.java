package com.hackerrank.algorithms.search.pairs;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Solution {
    public static void main(String... args) {
        System.out.println(execute(System.in));
    }

    public static String execute(InputStream inputStream) {
        StringBuffer output = new StringBuffer();
        Scanner scanner = new Scanner(inputStream);

        int N = scanner.nextInt();
        int K = scanner.nextInt();
        scanner.nextLine();

        int[] numbers = Arrays.stream(scanner.nextLine().split(" "))
                .map(String::trim).mapToInt(Integer::parseInt).toArray();
        List<Integer> numbersList = IntStream.of(numbers).boxed().collect(Collectors.toList());
        Collections.sort(numbersList, Collections.reverseOrder());
        Set<Integer> numbersSet = new HashSet<Integer>(){{ addAll(numbersList); }};

        int pairs = 0;
        for (Integer number: numbersList) {
            if (number <= K) break;
            pairs += numbersSet.contains(number - K) ? 1 : 0;
        }

        output.append(pairs);
        return output.toString();
    }
}
