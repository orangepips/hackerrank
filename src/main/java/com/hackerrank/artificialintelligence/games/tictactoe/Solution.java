package com.hackerrank.artificialintelligence.games.tictactoe;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

// https://www.hackerrank.com/challenges/tic-tac-toe
public class Solution {
    static TicTacToeBoard createTicTacToeBoard(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream);
        char who = scanner.nextLine().toCharArray()[0];
        TicTacToeBoard ticTacToeBoard = new TicTacToeBoard(who);
        for (int x = 0; x < TicTacToeBoard.BOARD_SIZE; x++) {
            char[] positions = scanner.nextLine().toCharArray();
            for (int y = 0; y < TicTacToeBoard.BOARD_SIZE; y++) {
                ticTacToeBoard.set(x, y, positions[y]);
            }
        }
        return ticTacToeBoard;
    }

    public static String execute(InputStream inputStream) {
        StringBuffer output = new StringBuffer();

        TicTacToeBoard ticTacToeBoard = createTicTacToeBoard(inputStream);

        System.out.println(ticTacToeBoard);
        System.out.println(ticTacToeBoard.calculateScore());
        return output.toString();
    }

    public static void main(String... args) {
        System.out.println(execute(System.in));
    }

    public static void play() {
        InputStream inputStream = new ByteArrayInputStream("X\n___\n___\n___".getBytes(StandardCharsets.UTF_8));
        TicTacToeBoard ticTacToeBoard = createTicTacToeBoard(inputStream);
        System.out.println(ticTacToeBoard);
        while (!ticTacToeBoard.hasWinner() || !ticTacToeBoard.isComplete()) {
            System.out.println(Arrays.toString(ticTacToeBoard.makeMove()));
            ticTacToeBoard.toggleWho();
            System.out.println(ticTacToeBoard.isWinner('X'));
            System.out.println(ticTacToeBoard.isWinner('O'));
            System.out.println(ticTacToeBoard);
        }
    }

    static class TicTacToeBoard {
        public static final int BOARD_SIZE = 3;
        public static final char EMPTY = '_';
        public static final int WIN = 10;
        public static final HashMap<Character, Integer> MARKER_TO_INT = new HashMap<Character, Integer>(){{
            put('X', 1);
            put('_', 0);
            put('O', -1);
        }};

        public static final HashMap<Integer, Character> INT_TO_MARKER = new HashMap<Integer, Character>(){{
            for (Character c: MARKER_TO_INT.keySet()) {
                put(MARKER_TO_INT.get(c), c);
            }
        }};

        HashSet<HashSet<Integer>> WINNING_BOARDS = new HashSet<HashSet<Integer>>(){{
            // horizontal
            add(tuple(0, 1, 2));
            add(tuple(3, 4, 5));
            add(tuple(6, 7, 8));
            // vertical
            add(tuple(0, 3, 6));
            add(tuple(1, 4, 7));
            add(tuple(2, 5, 8));
            // diagonal
            add(tuple(0, 4, 8));
            add(tuple(2, 4, 6));
        }};

        char who;
        int moves;
        /**
         * 0 1 2
         *
         * 0 1 2  0
         * 3 4 5  1
         * 6 7 8  2
         */
        int[] board = new int[BOARD_SIZE * BOARD_SIZE];

        public TicTacToeBoard(char who) {
            this.who = who;
        }

        /**
         * Copy constructor
         * @param ticTacToeBoard
         */
        public TicTacToeBoard(TicTacToeBoard ticTacToeBoard) {
            this.moves = moves;
            this.who = who;
            for (int i = 0; i < ticTacToeBoard.board.length; i++) {
                this.board[i] = ticTacToeBoard.board[i];
            }
        }

        static int[] int2xy(int p) {
           return new int[] {p % BOARD_SIZE, p / BOARD_SIZE};
        }

        private static HashSet<Integer> tuple(int... values) {
            HashSet<Integer> tuple = new HashSet<>();
            for (int value: values) {
                tuple.add(value);
            }
            return tuple;
        }

        static int xy2Int(int x, int y) {
            return y * BOARD_SIZE + x;
        }

        public int calculateScore() {
            return calculateScore(who);
        }

        public int calculateScore(char maxPlayerMarker) {
            HashMap<Character, HashSet<Integer>> playerMarkers = getPlayerMarkers(maxPlayerMarker);
            char minPlayerMarker = (maxPlayerMarker == 'X' ? 'O' : 'X');
            for (HashSet<Integer> WINNING_BOARD: WINNING_BOARDS) {
                if (playerMarkers.get(maxPlayerMarker).containsAll(WINNING_BOARD) || playerMarkers.get(minPlayerMarker).containsAll(WINNING_BOARD))
                    return WIN;
            }
            return 0;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TicTacToeBoard that = (TicTacToeBoard) o;
            return Objects.equals(board, that.board);
        }

        public char get(int x, int y) {
            return INT_TO_MARKER.get(board[xy2Int(x, y)]);
        }

        public HashSet<int[]> getAvailableMoves() {
            HashSet<int[]> availableMoves = new HashSet<>();
            for (int i = 0; i < board.length; i++) {
                if (board[i] == MARKER_TO_INT.get('_')) availableMoves.add(int2xy(i));
            }
            return availableMoves;
        }

        public HashMap<Character, HashSet<Integer>> getPlayerMarkers(char maxPlayerMarker) {
            char minPlayerMarker = (maxPlayerMarker == 'X' ? 'O' : 'X');
            HashSet<Integer> maxPlayer = new HashSet<>();
            HashSet<Integer> minPlayer = new HashSet<>();
            for (int i = 0; i < board.length; i++) {
                char marker = INT_TO_MARKER.get(board[i]);
                if (marker == maxPlayerMarker) maxPlayer.add(i);
                else if (marker == (maxPlayerMarker == 'X' ? 'O' : 'X')) minPlayer.add(i);
            }
            HashMap<Character, HashSet<Integer>> playerMarkers = new HashMap<>();
            playerMarkers.put(maxPlayerMarker, maxPlayer);
            playerMarkers.put(minPlayerMarker, minPlayer);
            return playerMarkers;
        }

        public boolean hasWinner() {
            return isWinner('X') || isWinner('O');
        }

        @Override
        public int hashCode() {
            return Objects.hash(board);
        }

        public boolean isComplete() {
            return getAvailableMoves().size() == 0;
        }

        public boolean isWinner() {
            return isWinner(who);
        }

        public boolean isWinner(char playerMarker) {
            return calculateScore(playerMarker) == WIN;
        }

        public int[] makeMove() {
            int[] nextMove = minimax();
            System.out.println(Arrays.toString(nextMove));
            mark(nextMove[1], nextMove[2], who);
            return nextMove;
        }

        public void mark(int x, int y, char player) {
            moves += 1;
            board[xy2Int(x, y)] = MARKER_TO_INT.get(player);
        }

        public void set(int x, int y, char marker) {
            moves += (marker == EMPTY ? 0 : 1);
            board[xy2Int(x, y)] = MARKER_TO_INT.get(marker);
        }

        @Override
        public String toString() {
            StringBuffer sb = new StringBuffer();
            StringBuffer tabsBuffer = new StringBuffer();
            for (int i = 0; i < moves; i++) {
                tabsBuffer.append("\t");
            }
            String tabs = tabsBuffer.toString();
            sb.append(tabs + this.who + " " + moves + "\n");
            for (int x = 0; x < BOARD_SIZE; x++) {
                sb.append(tabs);
                for (int y = 0; y < BOARD_SIZE; y++) {
                    sb.append(get(x, y));
                }
                if (x == BOARD_SIZE - 1) continue;
                sb.append('\n');
            }
            return sb.toString();
        }

        public char toggleWho() {
            this.who = (who == 'X' ? 'O' : 'X');
            return this.who;
        }

        /**
         * https://www.ntu.edu.sg/home/ehchua/programming/java/JavaGame_TicTacToe_AI.html
         * @return {score, x, y}
         */
        private int[] minimax() {
            HashSet<int[]> availableMoves = getAvailableMoves();

            int bestScore = WIN * (who == 'X' ? -1 : 1);
            int currentScore;
            int[] scoreAndBestMove = new int[] {0, -1, -1};

            if (availableMoves.size() == 0 || hasWinner()) {
                int score = calculateScore() ;
                //scoreAndBestMove[0] = score + (who == 'X' ? -moves : moves);
                return scoreAndBestMove;
            }

            for (int[] availableMove: availableMoves) {
                TicTacToeBoard ticTacToeBoard = new TicTacToeBoard(this);
                ticTacToeBoard.mark(availableMove[0], availableMove[1], who);
                ticTacToeBoard.toggleWho();
                currentScore = ticTacToeBoard.minimax()[0];
                if (currentScore > bestScore) {
                    scoreAndBestMove = new int[] {currentScore, availableMove[0], availableMove[1]};
                }
            }
            return scoreAndBestMove;
        }
    }
}
