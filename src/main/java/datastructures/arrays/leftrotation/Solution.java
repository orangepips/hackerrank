package datastructures.arrays.leftrotation;

import java.util.*;

public class Solution {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int d = scanner.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }
        rotateLeft(a, d);
        System.out.println(Arrays.toString(a).replaceAll(",", "").replace("[","").replace("]",""));
    }

    // http://www.programcreek.com/2015/03/rotate-array-in-java/
    public static void rotateLeft(int[] arr, int order) {
        if (arr == null || arr.length==0 || order < 0) {
            throw new IllegalArgumentException("Illegal argument!");
        }

        if(order > arr.length){
            order = order %arr.length;
        }

        //length of first part
        int a = order;

        reverse(arr, 0, a-1);
        reverse(arr, a, arr.length-1);
        reverse(arr, 0, arr.length-1);

    }

    public static void reverse(int[] arr, int left, int right){
        if(arr == null || arr.length == 1)
            return;

        while(left < right){
            int temp = arr[left];
            arr[left] = arr[right];
            arr[right] = temp;
            left++;
            right--;
        }
    }
}
