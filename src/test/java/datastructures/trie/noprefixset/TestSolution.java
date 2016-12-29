package datastructures.trie.noprefixset;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestSolution {
    List<String> fullClassNamePieces = Arrays.asList(this.getClass().getName().split("\\."));
    final String PACKAGE_DIRECTORY = "/" + StringUtils.join(fullClassNamePieces.subList(0, fullClassNamePieces.size() - 1), '/');

    void test(int n) {
        String result = Solution.execute(getInput(n));
        Assert.assertEquals(convertStreamToString(getOutput(n)), result);
    }

    @Test
    public void test0() {
        test(0);
    }

    @Test
    public void test39() {
        test(39);
    }

    InputStream getInput(int n) {
        return this.getClass().getResourceAsStream(PACKAGE_DIRECTORY + "/input" + n + ".txt");
    }

    InputStream getOutput(int n) {
        return this.getClass().getResourceAsStream(PACKAGE_DIRECTORY + "/output" + n + ".txt");
    }

    static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}
