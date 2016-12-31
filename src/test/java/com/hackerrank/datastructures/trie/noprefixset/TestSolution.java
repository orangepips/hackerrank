package com.hackerrank.datastructures.trie.noprefixset;

import com.hackerrank.TestBase;
import org.junit.Test;

import java.io.InputStream;

public class TestSolution extends TestBase {
    public String execute(InputStream inputStream) {
        return Solution.execute(inputStream);
    }

    @Test
    public void test0() {
        test(0);
    }

    @Test
    public void test39() {
        test(39);
    }
}
