package com.hackerrank.datastructures.trees.swapnodes;

import java.io.InputStream;
import java.util.*;

// https://www.hackerrank.com/challenges/swap-nodes-algo
public class Solution {
    public static void main(String... args) {
        System.out.println(execute(System.in));
    }

    /**
     * Input Format
     * First line of input contains N, number of nodes in tree.
     * Then N lines follow. Here each of ith line (1 <= i <= N) contains two integers, a b,
     * where a is the index of left child, and b is the index of right child of ith node. -1 is used to
     * represent null node.
     * Next line contain an integer, T. Then again T lines follows.
     * Each of these line contains an integer K.
     *
     * Given a tree and a integer, K, we have to swap the subtrees of all
     * the nodes who are at depth h, where h ∈ [K, 2K, 3K,...].
     * You are given a tree of N nodes where nodes are indexed from [1..N] and it is rooted at 1.
     * You have to perform T swap operations on it, and after each swap operation print the inorder traversal of the
     * current state of the tree.
     *
     * Output Format
     * For each K, perform swap operation as mentioned above and print the inorder traversal of
     * the current state of tree.
     * @param inputStream
     * @return
     */
    public static String execute(InputStream inputStream) {
        StringBuffer output = new StringBuffer();
        Scanner scanner = new Scanner(inputStream);
        BinaryTree binaryTree = new BinaryTree();
        int n = scanner.nextInt();
        for (int i = 1; i < n; i++) {
            binaryTree.addNode();
        }

        for (int j = 1; j <= n; j++) {
            int left = scanner.nextInt();
            int right = scanner.nextInt();
            BinaryTree.Node node = binaryTree.getNode(j);

            if (left != -1) node.setLeft(left);
            if (right != -1) node.setRight(right);
        }

        int k = scanner.nextInt();
        for (int x = 0; x < k; x++) {
            int h = scanner.nextInt();
            binaryTree.swap(h);

            output.append(binaryTree.inOrderTraversal());
            if (x != k - 1) {
                output.append("\n");
            }
        }
        return output.toString();
    }

    static public class BinaryTree {
        private ArrayList<Integer> inOrderIds = new ArrayList<Integer>();
        Node root;
        HashMap<Integer, HashSet<Node>> nodesByDepth = new HashMap<Integer, HashSet<Node>>();
        ArrayList<Node> nodes = new ArrayList<>();

        {
            root = new Node(1);
            root.id = 1;
            root.depth = 1;
            nodes.add(root);
            indexNodeByDepth(root);
        }

        public Node addNode() {
            Node node = new Node(nodes.size() + 1);
            nodes.add(node);
            return node;
        }

        public Node getNode(int id) {
            return nodes.get(id - 1);
        }

        @Override
        public String toString() {
            return "BinaryTree{" +
                    "root=" + root +
                    '}';
        }

        /**
         * Traverse the left subtree.
         * Visit root (print it).
         * Traverse the right subtree.
         */
        public String inOrderTraversal() {
            inOrderIds.clear();
            generateInOrderIds(root);
            StringBuilder sb = new StringBuilder();
            for (Integer id: inOrderIds) {
                sb.append(id + " ");
            }
            return sb.toString().trim();
        }

        void swap(int depth) {
            int index = 1;
            while (true) {
                int currentDepth = depth * index;
                HashSet<Node> parentNodes = nodesByDepth.get(currentDepth);
                if (parentNodes == null) break;
                for (Node parentNode : parentNodes) {
                    Node oldLeft = parentNode.left;
                    parentNode.left = parentNode.right;
                    parentNode.right = oldLeft;
                }
                index++;
            }
        }

        private void generateInOrderIds(Node node) {
            if (node == null) return;

            generateInOrderIds(node.left);
            inOrderIds.add(node.id);
            generateInOrderIds(node.right);
        }

        private void indexNodeByDepth(Node node) {
            if (nodesByDepth.get(node.depth) == null) {
                HashSet<Node> nodes = new HashSet<>();
                nodesByDepth.put(node.depth, nodes);
            }
            nodesByDepth.get(node.depth).add(node);
        }

        public class Node {
            int id;
            int depth = 1;
            Node left;
            Node right;

            private Node(int id) {
                this.id = id;
            }

            public void setLeft(int id) {
                Node node = getNode(id);
                left = node;
                left.depth = depth + 1;
                indexNodeByDepth(node);
            }

            public void setRight(int id) {
                Node node = getNode(id);
                right = node;
                right.depth = depth + 1;
                indexNodeByDepth(node);
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                Node node = (Node) o;
                return id == node.id;
            }

            @Override
            public int hashCode() {
                return Objects.hash(id);
            }

            @Override
            public String toString() {
                String tabs = new String(new char[depth]).replace("\0", "\t");
                return "\n" + tabs + "Node{" +
                        "id=" + id +
                        ", depth=" + depth +
                        ", left=" + left +
                        ", right=" + right +
                        '}';
            }
        }
    }
}
