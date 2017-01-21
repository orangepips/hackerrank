package com.hackerrank.artificialintelligence.games.tictactoe;

import com.hackerrank.TestBase;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;
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
    public void TestExecute() {
        System.out.println(Solution.execute(new ByteArrayInputStream("X\n___\n___\n___".getBytes())));
    }

    @Test
    public void TestNextMoveStartX() throws UnsupportedEncodingException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        System.setOut(ps);
        Solution.nextMove("X", new String[] {"___", "___", "___"});
        Assert.assertEquals("1 1\n", baos.toString("UTF-8"));
    }

    // https://www.quora.com/Is-there-a-way-to-never-lose-at-Tic-Tac-Toe
    @Test
    public void TestNextMoveXCenterOEdgeEnsureXCorner() throws UnsupportedEncodingException {
        // Y X format
        String[] edges = new String[] {"0 1", "1 0", "1 2", "2 1"};
        String[] corners = new String[] {"[0, 0]", "[0, 2]", "[2, 0]", "[2, 2]"};

        for (String edge: edges) {
            String[] coords = edge.split(" ");
            Solution.TicTacToeBoard ticTacToeBoard = Solution.createTicTacToeBoard("X", new String[]{"___", "_X_", "___"});
            ticTacToeBoard.mark(new Integer(coords[1]), new Integer(coords[0]), ticTacToeBoard.O);
            int[] nextMove = ticTacToeBoard.makeMove();
            Assert.assertTrue(ticTacToeBoard.toString(),Arrays.binarySearch(corners, Arrays.toString(nextMove)) > -1);

            ticTacToeBoard.toggleWhoseTurn();
            Solution.TicTacToeBoard ticTacToeBoardPlayed = Solution.play(ticTacToeBoard.toString());
            Assert.assertTrue(ticTacToeBoardPlayed.toString(), ticTacToeBoardPlayed.isWinner('X'));
        }
    }

    @Test
    public void TestNextMoveXCenterOCorner() throws UnsupportedEncodingException {
        // Y X format
        String[] corners = new String[] {"0 0", "0 2", "2 0", "2 2"};

        for (String corner: corners) {
            String[] coords = corner.split(" ");
            Solution.TicTacToeBoard ticTacToeBoard = Solution.createTicTacToeBoard("X", new String[]{"___", "_X_", "___"});
            ticTacToeBoard.mark(new Integer(coords[1]), new Integer(coords[0]), ticTacToeBoard.O);
            ticTacToeBoard.makeMove();
            ticTacToeBoard.toggleWhoseTurn();
            Solution.TicTacToeBoard ticTacToeBoardPlayed = Solution.play(ticTacToeBoard.toString());
            Assert.assertFalse(ticTacToeBoardPlayed.toString(), ticTacToeBoardPlayed.hasWinner());
        }
    }

    @Test
    public void TestNextMoveOCenterEnsureXCorner() {
        String[] corners = new String[] {"[0, 0]", "[0, 2]", "[2, 0]", "[2, 2]"};
        Solution.TicTacToeBoard ticTacToeBoard = Solution.createTicTacToeBoard("X", new String[]{"___", "_O_", "___"});
        int[] nextMove = ticTacToeBoard.makeMove();
        Assert.assertTrue(ticTacToeBoard.toString(),Arrays.binarySearch(corners, Arrays.toString(nextMove)) > -1);

        ticTacToeBoard.toggleWhoseTurn();
        Solution.TicTacToeBoard ticTacToeBoardPlayed = Solution.play(ticTacToeBoard.toString());
        Assert.assertFalse(ticTacToeBoardPlayed.toString(), ticTacToeBoardPlayed.hasWinner());
    }

    @Test
    public void TestNextMoveOCornerEnsureXCenter() {
        // Y X format
        String[] corners = new String[] {"0 0", "0 2", "2 0", "2 2"};

        for (String corner: corners) {
            String[] coords = corner.split(" ");
            Solution.TicTacToeBoard ticTacToeBoard = Solution.createTicTacToeBoard("O", new String[]{"___", "___", "___"});
            ticTacToeBoard.mark(new Integer(coords[1]), new Integer(coords[0]), ticTacToeBoard.O);
            ticTacToeBoard.toggleWhoseTurn(Boolean.TRUE);
            int[] nextMove = ticTacToeBoard.makeMove();
            Assert.assertArrayEquals(ticTacToeBoard.toString(), nextMove, new int[] {1,1});

            ticTacToeBoard.toggleWhoseTurn();
            Solution.TicTacToeBoard ticTacToeBoardPlayed = Solution.play(ticTacToeBoard.toString());
            Assert.assertFalse(ticTacToeBoardPlayed.toString(), ticTacToeBoardPlayed.hasWinner());
        }
    }

    @Test
    public void TestNextMoveOEdgeEnsureXAdjacentEdge() {
        String[] edges = new String[] {"0 1", "1 0", "1 2", "2 1"};
        String[] corners = new String[] {"[0, 0]", "[0, 2]", "[2, 0]", "[2, 2]"};
        for (String edge: edges) {
            String[] coords = edge.split(" ");
            Solution.TicTacToeBoard ticTacToeBoard = Solution.createTicTacToeBoard("O", new String[]{"___", "___", "___"});
            int x = new Integer(coords[1]);
            int y = new Integer(coords[0]);
            ticTacToeBoard.mark(x, y, ticTacToeBoard.O);
            ticTacToeBoard.toggleWhoseTurn();
            int[] nextMove = ticTacToeBoard.makeMove();
            System.out.println("nextMove:\t" + Arrays.toString(nextMove) + "\t" + Arrays.binarySearch(corners, Arrays.toString(nextMove)));
            Assert.assertTrue(ticTacToeBoard.toString(), (nextMove[0] == x || nextMove[1] == y)
                    && (Arrays.binarySearch(corners, Arrays.toString(nextMove)) > -1));

            ticTacToeBoard.toggleWhoseTurn();
            Solution.TicTacToeBoard ticTacToeBoardPlayed = Solution.play(ticTacToeBoard.toString());
            Assert.assertFalse(ticTacToeBoardPlayed.toString(), ticTacToeBoardPlayed.hasWinner());
        }
    }

    @Test
    public void TestIsComplete() {
        Solution.TicTacToeBoard ticTacToeBoardA = Solution.createTicTacToeBoard(getInput(2000));
        Assert.assertFalse(ticTacToeBoardA.isComplete());

        Solution.TicTacToeBoard ticTacToeBoardB = Solution.createTicTacToeBoard(getInput(2001));
        Assert.assertTrue(ticTacToeBoardB.isComplete());
    }

    @Test
    public void TestIsWinner() {
        Solution.TicTacToeBoard ticTacToeBoard = Solution.createTicTacToeBoard(getInput(2000));
    }

    @Test
    public void Testxy2Int() {
        Assert.assertEquals(Solution.TicTacToeBoard.xy2Int(0, 0), 0);
    }

    @Override
    public String execute(InputStream inputStream) {
        return Solution.execute(inputStream);
    }

    private ByteArrayOutputStream rerouteSysOut() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        System.setOut(ps);
        return baos;
    }
}
