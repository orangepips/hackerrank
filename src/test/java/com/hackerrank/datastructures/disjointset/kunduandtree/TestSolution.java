package com.hackerrank.datastructures.disjointset.kunduandtree;

import com.hackerrank.TestBase;
import org.junit.Test;

import java.io.InputStream;

public class TestSolution extends TestBase {
    @Override
    public String execute(InputStream inputStream) {
        return Solution.execute(inputStream);
    }

    @Test
    public void Test1() {
        test(1);
    }

    @Test
    public void Test8() {
        test(8);
    }

    @Test
    public void Test1000() {
        test(1000);
    }

    @Test
    public void Test2000() {
        test(2000);
    }
}
