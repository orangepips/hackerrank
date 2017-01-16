package com.hackerrank.algorithms.sorting.countingsort4;

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

    @Test
    public void Test5() {
        test(5);
    }
}
