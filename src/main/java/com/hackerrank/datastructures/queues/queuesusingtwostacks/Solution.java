package com.hackerrank.datastructures.queues.queuesusingtwostacks;

import java.util.Scanner;
import java.util.Stack;

public class Solution {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StackQueue<Integer> stackQueue = new StackQueue<Integer>();
        int q = scanner.nextInt();
        for (int i = 0; i < q; i++) {
            int type = scanner.nextInt();
            switch (type) {
                case 1: // Enqueue element x into the end of the queue.
                    int x = scanner.nextInt();
                    stackQueue.push(x);
                    break;
                case 2: // Dequeue the element at the front of the queue
                    stackQueue.pop();
                    break;
                case 3: // Print the element at the front of the queue.
                    System.out.println(stackQueue.peek());
                    break;
            }

        }
    }

    static class StackQueue<T> {
        private Stack<T> s1 = new Stack<T>();
        private Stack<T> s2 = new Stack<T>();

        public void push(T obj) {
            s1.push(obj);
        }

        public T pop() {
            transfer();
            return s2.pop();
        }

        public T peek() {
            transfer();
            return s2.peek();
        }

        private void transfer() {
            if (s2.isEmpty()) {
                if (s1.isEmpty()) {
                    return;
                }
                while (!s1.isEmpty()) {
                    s2.push(s1.pop());
                }
            }
        }
    }
}
