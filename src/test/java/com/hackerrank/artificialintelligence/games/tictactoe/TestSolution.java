package com.hackerrank.artificialintelligence.games.tictactoe;

import com.hackerrank.TestBase;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;

public class TestSolution extends TestBase {
    @Test
    public void Tesint2xy() {
        Assert.assertArrayEquals(Solution.TicTacToeBoard.int2xy(8), new int[] {2, 2});
    }

    @Test
    public void Test1000() {
        test(1000);
    }

    @Test
    public void TestGetAvailableMoves() {
       HashSet<int[]> availableMoves = Solution.createTicTacToeBoard(getInput(1000)).getAvailableMoves();
       for (int[] move: availableMoves) {
           System.out.println(Arrays.toString(move));
       }
    }

    @Test
    public void TestPlay() {
        Solution.play();
    }

    @Test
    public void TestIsComplete() {
        Solution.TicTacToeBoard ticTacToeBoardA = Solution.createTicTacToeBoard(getInput(2000));
        Assert.assertFalse(ticTacToeBoardA.isComplete());

        Solution.TicTacToeBoard ticTacToeBoardB = Solution.createTicTacToeBoard(getInput(2001));
        Assert.assertTrue(ticTacToeBoardB.isComplete());
        Assert.assertFalse(ticTacToeBoardB.isWinner('O'));
        Assert.assertFalse(ticTacToeBoardB.isWinner('X'));
    }

    @Test
    public void TestIsWinner() {
        Solution.TicTacToeBoard ticTacToeBoard = Solution.createTicTacToeBoard(getInput(2000));
        Assert.assertFalse(ticTacToeBoard.isWinner());
        Assert.assertTrue(ticTacToeBoard.isWinner('X'));
    }

    @Test
    public void Testxy2Int() {
        Assert.assertEquals(Solution.TicTacToeBoard.xy2Int(0, 0), 0);
    }

    @Override
    public String execute(InputStream inputStream) {
        return Solution.execute(inputStream);
    }
}
