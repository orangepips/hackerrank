package com.hackerrank.algorithms.search.pairs;

import com.hackerrank.TestBase;
import org.junit.Test;

import java.io.InputStream;

public class TestSolution extends TestBase {
    @Override
    public String execute(InputStream inputStream) {
        return Solution.execute(inputStream);
    }

    @Test
    public void Test1000() {
        test(1000);
    }
}
