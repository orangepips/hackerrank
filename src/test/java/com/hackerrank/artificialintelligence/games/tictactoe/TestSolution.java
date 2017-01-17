package com.hackerrank.artificialintelligence.games.tictactoe;

import com.hackerrank.TestBase;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
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
    public void Test2002() { test(2002); }

    @Test
    public void TestGetAvailableMoves() {
       HashSet<int[]> availableMoves = Solution.createTicTacToeBoard(getInput(1000)).getAvailableMoves();
       for (int[] move: availableMoves) {
           System.out.println(Arrays.toString(move));
       }
    }

    @Test
    public void TestCalculateScore() {
        Assert.assertEquals(Solution.TicTacToeBoard.ONESIE * 4,
                Solution.createTicTacToeBoard(new ByteArrayInputStream("X\n___\n_X_\n___".getBytes(StandardCharsets.UTF_8))).calculateScore());
        Assert.assertEquals(Solution.TicTacToeBoard.ONESIE * (2 - 3),
                Solution.createTicTacToeBoard(new ByteArrayInputStream("O\n___\n_X_\nO__".getBytes(StandardCharsets.UTF_8))).calculateScore());
        Assert.assertEquals(Solution.TicTacToeBoard.ONESIE * (10 + 2 - 1),
                Solution.createTicTacToeBoard(new ByteArrayInputStream("X\n___\nXX_\nO__".getBytes(StandardCharsets.UTF_8))).calculateScore());
        Assert.assertEquals(Solution.TicTacToeBoard.WIN,
                Solution.createTicTacToeBoard(new ByteArrayInputStream("X\n_O_\nXXX\nO__".getBytes(StandardCharsets.UTF_8))).calculateScore());
    }

    @Test
    public void TestPlay() {
        Solution.play();
    }

    @Test
    public void TestPlayXOO_O11_X22() {
        Solution.play("O\nX__\n_O_\n__X");
    }

    @Test
    public void TestPlayX00_O01_X10() {
        Solution.play("O\nXO_\nXXO\nO__");
    }

    @Test
    public void TestPlayBlank() {
        Solution.play("X\n___\n___\n___");
    }

    @Test
    public void TestExecute() {
        System.out.println(Solution.execute(new ByteArrayInputStream("X\n___\n___\n___".getBytes())));
    }

    @Test
    public void TestNextMoveBlank() {
        Solution.nextMove("X", new String[] {"___", "___", "___"});
    }

    @Test
    public void TestNextMove1000() {
        Solution.nextMove("O", new String[] {"___", "OXX", "_XO"});
    }

    @Test
    public void TestIsComplete() {
        Solution.TicTacToeBoard ticTacToeBoardA = Solution.createTicTacToeBoard(getInput(2000));
        Assert.assertFalse(ticTacToeBoardA.isComplete());

        Solution.TicTacToeBoard ticTacToeBoardB = Solution.createTicTacToeBoard(getInput(2001));
        Assert.assertTrue(ticTacToeBoardB.isComplete());
//        Assert.assertFalse(ticTacToeBoardB.isWinner('O'));
//        Assert.assertFalse(ticTacToeBoardB.isWinner('X'));
    }

    @Test
    public void TestIsWinner() {
        Solution.TicTacToeBoard ticTacToeBoard = Solution.createTicTacToeBoard(getInput(2000));
//        Assert.assertFalse(ticTacToeBoard.isWinner());
//        Assert.assertTrue(ticTacToeBoard.isWinner('X'));
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
