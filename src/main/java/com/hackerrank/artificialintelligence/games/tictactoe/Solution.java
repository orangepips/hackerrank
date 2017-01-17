package com.hackerrank.artificialintelligence.games.tictactoe;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

// https://www.hackerrank.com/challenges/tic-tac-toe
public class Solution {
    static TicTacToeBoard createTicTacToeBoard(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream);
        String player;
        String board[] = new String[3];

        //If player is X, I'm the first player.
        //If player is O, I'm the second player.
        player = scanner.nextLine();

        //Read the board now. The board is a 3x3 array filled with X, O or _.
        for(int i = 0; i < 3; i++) {
            board[i] = scanner.next();
        }
        return createTicTacToeBoard(player, board);
    }

    static TicTacToeBoard createTicTacToeBoard(String player, String [] board) {
        char who = player.toCharArray()[0];
        TicTacToeBoard ticTacToeBoard = new TicTacToeBoard(who);
        for (int y = 0; y < board.length; y++) {
            char[] positions = board[y].toCharArray();
            for (int x = 0; x < TicTacToeBoard.BOARD_SIZE; x++) {
                ticTacToeBoard.set(x, y, positions[x]);
            }
        }
        return ticTacToeBoard;
    }

    public static String execute(InputStream inputStream) {
        StringBuffer output = new StringBuffer();

        TicTacToeBoard ticTacToeBoard = createTicTacToeBoard(inputStream);
        int[] nextMove = ticTacToeBoard.makeMove();

        output.append(nextMove[1] + " " + nextMove[0]);
        return output.toString();
    }

    public static void main(String... args) {
        System.out.println(execute(System.in));
    }

    static void nextMove(String player, String [] board) {
        TicTacToeBoard ticTacToeBoard = createTicTacToeBoard(player, board);
        int[] nextMove = ticTacToeBoard.makeMove();
        System.out.println(nextMove[1] + " " + nextMove[0]);
    }

    public static void play() {
        play("O\nX__\n___\n___");
    }

    public static void play(String input) {
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        TicTacToeBoard ticTacToeBoard = createTicTacToeBoard(inputStream);
        System.out.println(ticTacToeBoard);
        while (!ticTacToeBoard.hasWinner() && !ticTacToeBoard.isComplete()) {
            System.out.println(Arrays.toString(ticTacToeBoard.board));
            System.out.println(Arrays.toString(ticTacToeBoard.makeMove()));
            ticTacToeBoard.toggleWhoseTurn();
//            System.out.println(ticTacToeBoard.hasWinner());
//            System.out.println(ticTacToeBoard.isComplete());
            System.out.println(ticTacToeBoard);
        }
    }

    static class TicTacToeBoard {
        public static final int BOARD_SIZE = 3;
        public static final char EMPTY = '_';
        public static final char X = 'X';
        public static final char O = 'O';
        public static final int WIN = 100;
        public static final int TWOSIE = 10;
        public static final int ONESIE = 1;
        public static final HashMap<Character, Integer> MARKER_TO_INT = new HashMap<Character, Integer>(){{
            put(X, 1);
            put(EMPTY, 0);
            put(O, -1);
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

        char maximizingPlayerMarker;
        char minimizingPlayerMaker;

        char whoseTurn;
        int moves;
        /**
         * 0 1 2
         *
         * 0 1 2  0
         * 3 4 5  1
         * 6 7 8  2
         */
        int[] board = new int[BOARD_SIZE * BOARD_SIZE];

        public TicTacToeBoard(char whoseTurn) {
            this.whoseTurn = whoseTurn;
            this.maximizingPlayerMarker = whoseTurn;
            this.minimizingPlayerMaker = (whoseTurn == X ? O : X);
        }

        /**
         * Copy constructor
         * @param ticTacToeBoard
         */
        public TicTacToeBoard(TicTacToeBoard ticTacToeBoard) {
            this.moves = ticTacToeBoard.moves;
            this.whoseTurn = ticTacToeBoard.whoseTurn;
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
            int score = 0;
            HashMap<Character, HashSet<Integer>> playerMarkers = getPlayerMarkers();

            for (HashSet<Integer> WINNING_BOARD: WINNING_BOARDS) {
                HashSet<Integer> maxPlayerMarkers = new HashSet<>(playerMarkers.get(maximizingPlayerMarker));
                HashSet<Integer> minPlayerMarkers = new HashSet<>(playerMarkers.get(minimizingPlayerMaker));
                HashSet<Integer> emptyMarkers = new HashSet<>(playerMarkers.get(EMPTY));
                if (playerMarkers.get(maximizingPlayerMarker).containsAll(WINNING_BOARD)) { return WIN; }
                else if (playerMarkers.get(minimizingPlayerMaker).containsAll(WINNING_BOARD)) { return -WIN; }
                else {
                    maxPlayerMarkers.retainAll(WINNING_BOARD);
                    minPlayerMarkers.retainAll(WINNING_BOARD);
                    emptyMarkers.retainAll(WINNING_BOARD);
                    if (maxPlayerMarkers.size() == 1 && emptyMarkers.size() == 2) score += ONESIE;
                    else if (minPlayerMarkers.size() == 1 && emptyMarkers.size() == 2) score -= ONESIE;
                    else if (maxPlayerMarkers.size() == 2 && emptyMarkers.size() == 1) score += TWOSIE;
                    else if (minPlayerMarkers.size() == 2 && emptyMarkers.size() == 1) score -= TWOSIE;
                }
            }
            return score;
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

        public HashMap<Character, HashSet<Integer>> getPlayerMarkers() {
            HashSet<Integer> maxPlayer = new HashSet<>();
            HashSet<Integer> minPlayer = new HashSet<>();
            HashSet<Integer> empty = new HashSet<>();
            for (int i = 0; i < board.length; i++) {
                char marker = INT_TO_MARKER.get(board[i]);
                if (marker == maximizingPlayerMarker) maxPlayer.add(i);
                else if (marker == minimizingPlayerMaker) minPlayer.add(i);
                else empty.add(i);
            }
            HashMap<Character, HashSet<Integer>> playerMarkers = new HashMap<>();
            playerMarkers.put(maximizingPlayerMarker, maxPlayer);
            playerMarkers.put(minimizingPlayerMaker, minPlayer);
            playerMarkers.put(EMPTY, empty);
            return playerMarkers;
        }

        public boolean hasWinner() {
            return Math.abs(calculateScore()) == WIN;
        }

        @Override
        public int hashCode() {
            return Objects.hash(board);
        }

        public boolean isComplete() {
            return getAvailableMoves().size() == 0;
        }

        public int[] makeMove() {
            int[] nextMove = minimax();
            mark(nextMove[1], nextMove[2], whoseTurn);
            return new int[]{nextMove[1], nextMove[2]};
        }

        public void mark(int x, int y, char player) {
            moves += (player == EMPTY ? -1 : 1);
            board[xy2Int(x, y)] = MARKER_TO_INT.get(player);
        }

        public void set(int x, int y, char marker) {
            moves += (marker == EMPTY ? 0 : 1);
            board[xy2Int(x, y)] = MARKER_TO_INT.get(marker);
        }

        @Override
        public String toString() {
            StringBuffer sb = new StringBuffer();
            sb.append(this.whoseTurn + " " + moves + "\n");
            for (int y = 0; y < BOARD_SIZE; y++) {
                for (int x = 0; x < BOARD_SIZE; x++) {
                    sb.append(get(x, y));
                }
                if (y == BOARD_SIZE - 1) continue;
                sb.append('\n');
            }
            return sb.toString();
        }

        public char toggleWhoseTurn() {
            this.whoseTurn = (whoseTurn == X ? O : X);
            this.maximizingPlayerMarker = whoseTurn;
            this.minimizingPlayerMaker = (whoseTurn == X ? O : X);

            return this.whoseTurn;
        }

        private int[] minimax() {
            return minimax(Integer.MIN_VALUE, Integer.MAX_VALUE);
        }

        /**
         * https://www.ntu.edu.sg/home/ehchua/programming/java/JavaGame_TicTacToe_AI.html
         * @return {score, x, y}
         */
        private int[] minimax(int alpha, int beta) {
            HashSet<int[]> availableMoves = getAvailableMoves();

            int score;
            int bestRow = -1;
            int bestCol = -1;

            if (availableMoves.size() == 9) {
                return new int[] {calculateScore(), 1, 1};
            }

            if (availableMoves.size() == 0 || hasWinner()) {
                score = calculateScore();
                // System.out.println("minimax > score:\t" + score);
                return new int[] {score, bestRow, bestCol};
            }

            for (int[] availableMove: availableMoves) {
                toggleWhoseTurn();
                mark(availableMove[0], availableMove[1], whoseTurn);
                score = minimax(alpha, beta)[0];
                // System.out.println(depth + " " + score + " " + alpha + " " + beta + " " + Arrays.toString(availableMove) + " " + whoseTurn);
                toggleWhoseTurn();

                if (whoseTurn == maximizingPlayerMarker) {
                    score -= moves;
                    if (score > alpha) {
                        alpha = score;
                        bestRow = availableMove[0];
                        bestCol = availableMove[1];
                    }
                } else {
                    score += moves;
                    if (score < beta) {
                        beta = score;
                        bestRow = availableMove[0];
                        bestCol = availableMove[1];
                    }
                }

                mark(availableMove[0], availableMove[1], EMPTY);
                if (alpha >= beta) break;
            }
            //System.out.println(moves + " " + (whoseTurn == maximizingPlayerMarker ? alpha : beta) + " [" + bestRow + ", " + bestCol + "]");
            return new int[] {(whoseTurn == maximizingPlayerMarker ? alpha : beta), bestRow, bestCol};
        }
    }
}
