package com.hackerrank.datastructures.trees.swapnodes;

import com.hackerrank.TestBase;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;

/**
 * Created by mlesko on 12/30/16.
 */
public class TestSolution extends TestBase {
    public String execute(InputStream inputStream) {
        return Solution.execute(inputStream);
    }

    @Test
    public void TestBinaryTree() {
        Solution.BinaryTree binaryTree = new Solution.BinaryTree();
        Assert.assertEquals(1, binaryTree.root.id);
        Assert.assertEquals(1, binaryTree.root.depth);

        Solution.BinaryTree.Node node2 = binaryTree.addNode();
        Assert.assertEquals(2, node2.id);
        Assert.assertEquals(1, node2.depth);

        Solution.BinaryTree.Node node3 = binaryTree.addNode();
        Assert.assertEquals(3, node3.id);
        Assert.assertEquals(1, node3.depth);

        binaryTree.root.setLeft(2);
        binaryTree.root.setRight(3);

        Assert.assertEquals(2, node2.depth);
        Assert.assertEquals(binaryTree.root.left, node2);
        Assert.assertEquals(2, node3.depth);
        Assert.assertEquals(binaryTree.root.right, node3);

        binaryTree.swap(1);
        Assert.assertEquals(binaryTree.root.left, node3);
        Assert.assertEquals(binaryTree.root.right, node2);
        Assert.assertEquals(binaryTree.inOrderTraversal(), "3 1 2");

        binaryTree.swap(1);
        Assert.assertEquals(binaryTree.root.left, node2);
        Assert.assertEquals(binaryTree.root.right, node3);
        Assert.assertEquals(binaryTree.inOrderTraversal(), "2 1 3");
    }

    @Test
    public void Test1000() {
        test(1000);
    }

    @Test
    public void Test1001() {
        test(1001);
    }

    @Test
    public void Test1002() {
        test(1002);
    }
}