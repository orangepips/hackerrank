package com.hackerrank;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public abstract class TestBase {
    List<String> fullClassNamePieces = Arrays.asList(this.getClass().getName().split("\\."));
    final String PACKAGE_DIRECTORY = "/" + StringUtils.join(fullClassNamePieces.subList(0, fullClassNamePieces.size() - 1), '/');

    public abstract String execute(InputStream inputStream);

    public void test(int n) {
        String result = execute(getInput(n));
        Assert.assertEquals(convertStreamToString(getOutput(n)), result);
    }

    public InputStream getInput(int n) {
        return this.getClass().getResourceAsStream(PACKAGE_DIRECTORY + "/input" + n + ".txt");
    }

    public InputStream getOutput(int n) {
        return this.getClass().getResourceAsStream(PACKAGE_DIRECTORY + "/output" + n + ".txt");
    }

    public static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}
