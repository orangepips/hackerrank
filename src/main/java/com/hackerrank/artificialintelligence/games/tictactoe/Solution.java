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

    public static TicTacToeBoard play(String input) {
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        TicTacToeBoard ticTacToeBoard = createTicTacToeBoard(inputStream);
        System.out.println(ticTacToeBoard);
        System.out.println("Max: " + ticTacToeBoard.maximizingPlayerMarker + "\tMin: " + ticTacToeBoard.minimizingPlayerMaker);
        while (true) {
            System.out.println(Arrays.toString(ticTacToeBoard.makeMove()));
            ticTacToeBoard.toggleWhoseTurn();
            input = ticTacToeBoard.toString();
            inputStream = new ByteArrayInputStream(input.getBytes());
            ticTacToeBoard = createTicTacToeBoard(inputStream);
            System.out.println(ticTacToeBoard);
            System.out.println("Max: " + ticTacToeBoard.maximizingPlayerMarker + "\tMin: " + ticTacToeBoard.minimizingPlayerMaker);
            if (ticTacToeBoard.hasWinner() || ticTacToeBoard.isComplete()) break;
         }
         return ticTacToeBoard;
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
            this(ticTacToeBoard.whoseTurn);
            this.moves = ticTacToeBoard.moves;
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
            String uuid = UUID.randomUUID().toString();
            int score = 0;
            HashMap<Character, HashSet<Integer>> playerMarkers = getPlayerMarkers();

            for (HashSet<Integer> WINNING_BOARD: WINNING_BOARDS) {
//                HashSet<Integer> maxPlayerMarkers = new HashSet<>(playerMarkers.get(maximizingPlayerMarker));
//                HashSet<Integer> minPlayerMarkers = new HashSet<>(playerMarkers.get(minimizingPlayerMaker));
//                HashSet<Integer> emptyMarkers = new HashSet<>(playerMarkers.get(EMPTY));
//                if (playerMarkers.get(maximizingPlayerMarker).containsAll(WINNING_BOARD)) {
//                    score += WIN;
//                }
//                else if (playerMarkers.get(minimizingPlayerMaker).containsAll(WINNING_BOARD)) {
//                    score += -WIN;
//                }
//                else {
//                    maxPlayerMarkers.retainAll(WINNING_BOARD);
//                    minPlayerMarkers.retainAll(WINNING_BOARD);
//                    emptyMarkers.retainAll(WINNING_BOARD);
//                    if (maxPlayerMarkers.size() == 1 && emptyMarkers.size() == 2) score += ONESIE;
//                    else if (minPlayerMarkers.size() == 1 && emptyMarkers.size() == 2) score -= ONESIE;
//                    else if (maxPlayerMarkers.size() == 2 && emptyMarkers.size() == 1) score += TWOSIE;
//                    else if (minPlayerMarkers.size() == 2 && emptyMarkers.size() == 1) score -= TWOSIE;
//                }
                if (playerMarkers.get(maximizingPlayerMarker).containsAll(WINNING_BOARD)) {
                    return 1;
                } else if (playerMarkers.get(minimizingPlayerMaker).containsAll(WINNING_BOARD)) {
                    return - 1;
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
            return isWinner(X) || isWinner(O);
        }

        public boolean isWinner(char playerMarker) {
            HashMap<Character, HashSet<Integer>> playerMarkers = getPlayerMarkers();
            for (HashSet<Integer> WINNING_BOARD: WINNING_BOARDS) {
                if (playerMarkers.get(playerMarker).containsAll(WINNING_BOARD)) return true;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(board);
        }

        public boolean isComplete() {
            return getAvailableMoves().size() == 0;
        }

        public int[] makeMove() {
//            int[] nextMove = minimax();
//            mark(nextMove[1], nextMove[2], whoseTurn);
//            return new int[]{nextMove[1], nextMove[2]};
//            int [] nextMove = getBestRedditMove();
//            System.out.println("nextMove: " + Arrays.toString(nextMove) + "\twhoseTurn: " + whoseTurn);
//            mark(nextMove[0], nextMove[1], whoseTurn);
//            return new int[]{nextMove[0], nextMove[1]};
            int[] nextMoveAndScore = neverStopBuilding(this, whoseTurn, moves, -1);
            System.out.println(Arrays.toString(nextMoveAndScore));
            int[] nextMove = int2xy(nextMoveAndScore[0]);
            System.out.println(Arrays.toString(nextMove));
            mark(nextMove[0], nextMove[1], whoseTurn);
            return new int[]{nextMove[0], nextMove[1]};
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
            sb.append(this.whoseTurn + "\n");
            sb.append(getBoard());
            return sb.toString();
        }

        public String getBoard() {
            StringBuffer sb = new StringBuffer();
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
            return toggleWhoseTurn(Boolean.FALSE);
        }

        public char toggleWhoseTurn(boolean swapMinAndMax) {
            this.whoseTurn = (whoseTurn == X ? O : X);
            if (swapMinAndMax) {
                this.maximizingPlayerMarker = whoseTurn;
                this.minimizingPlayerMaker = (whoseTurn == X ? O : X);
            }

            return this.whoseTurn;
        }

//        def value(grid, player, move):
//                """Recursive depth first search following Negamax algorithm."""
//                    grid = grid[:]
//                    grid[move] = player
//                if winner(grid, player):
//                        return 2
//                if grid.count(blank) == 0:
//                        return 1
//                    values = []
//                            for i in range(9):
//                            if grid[i] == blank:
//                            values.append(value(grid, -player, i))
//                            # assume the opponent's best move (max) and reverse it.
//                            return 2-max(values)


//        http://neverstopbuilding.com/minimax
        private int[] neverStopBuilding(TicTacToeBoard currentTicTacToeBoard, char player, int depth, int move) {
            if (currentTicTacToeBoard.isComplete() || hasWinner()) {
                return new int[] {move, neverStopBuildingScore(currentTicTacToeBoard, player, depth)};
            }
            HashSet<int[]> availableMoves = currentTicTacToeBoard.getAvailableMoves();
            depth += 1;
            ArrayList<Integer> scores = new ArrayList<>();
            ArrayList<Integer> moves = new ArrayList<>();
            for (int[] availableMove: availableMoves) {
                TicTacToeBoard nextTicTacToeBoard = new TicTacToeBoard(currentTicTacToeBoard);
                nextTicTacToeBoard.mark(availableMove[0], availableMove[1], player);
                int[] neverStopBuildingResult = nextTicTacToeBoard.neverStopBuilding(nextTicTacToeBoard, player, depth, xy2Int(availableMove[0], availableMove[1]));
                scores.add(neverStopBuildingResult[1]);
                moves.add(neverStopBuildingResult[0]);
            }

            int score = (currentTicTacToeBoard.whoseTurn == maximizingPlayerMarker ? Collections.max(scores) : Collections.min(scores));

            int scoreIndex = scores.indexOf(score);
            int nextMove = moves.get(scoreIndex);

            return new int[] {nextMove, scoreIndex};
        }

        private int neverStopBuildingScore(TicTacToeBoard currentTicTacToeBoard, char player, int depth) {
            if (currentTicTacToeBoard.isWinner(player)) {
                return 10 - depth;
            } else if (currentTicTacToeBoard.isWinner((player == X ? O : X))) {
                return depth - 10;
            } else {
                return 0;
            }
        }

        //        https://www.reddit.com/r/learnpython/comments/20mc2p/find_the_fallacy_in_my_tictactoe_game_minimax/
        private int[] getBestRedditMove() {
            HashSet<int[]> availableMoves = getAvailableMoves();
            int[] bestMove;
            int move = 0;
            int[] boardCopy = new int[board.length];
            System.arraycopy(board, 0, boardCopy, 0, board.length);
            for (int[] availableMove: availableMoves) {
                move = Math.max(move, redditMove(boardCopy, whoseTurn, xy2Int(availableMove[0], availableMove[1])));
            }
            return int2xy(move);
        }

        private int redditMove(int[] board, char player, int move) {
            board[move] = MARKER_TO_INT.get(player);
            if (isWinner(player)) {
                return 2;
            }
            if (isComplete()) {
                return 0;
            }
            ArrayList<Integer> values = new ArrayList<>();
            for (int i = 0; i < board.length; i++) {
                if (board[i] == EMPTY) {
                    char nextPlayer = (player == X ? O : X);
                    values.add(redditMove(board, nextPlayer, i));
                }
            }
            return 2 - (values.size() > 0 ? Collections.max(values) : 0);
        }

        int currentMove;
        private int[] minimax() {
            currentMove = moves;
            return minimax(maximizingPlayerMarker, Integer.MIN_VALUE, Integer.MAX_VALUE);
        }

        /**
         * https://www.ntu.edu.sg/home/ehchua/programming/java/JavaGame_TicTacToe_AI.html
         * @return {score, x, y}
         */
        private int[] minimax(char who, int alpha, int beta) {
            HashSet<int[]> availableMoves = getAvailableMoves();

            int score;
            int bestRow = -1;
            int bestCol = -1;

            if (availableMoves.size() == 0 || hasWinner()) {
                score = calculateScore();
                return new int[] {score, bestRow, bestCol};
            }

            int bestImmediateScore = 0;
            HashSet<int[]> bestMoves = new HashSet<>();
            for (int[] availableMove: availableMoves) {
                mark(availableMove[0], availableMove[1], whoseTurn);
                //System.out.println(whoseTurn + " " + moves % 2);
                int immediateScore = calculateScore();
                toggleWhoseTurn();

                if (who == maximizingPlayerMarker) {
                    score = minimax(minimizingPlayerMaker, alpha, beta)[0];
                    if (score >= alpha) {
                        bestMoves.add(new int[]{100, score, alpha, availableMove[0], availableMove[1], bestImmediateScore, immediateScore});
                    }
                    //score -= moves;
                    if (score > alpha || (score >= alpha && bestImmediateScore < immediateScore) || immediateScore >= WIN) {
                        alpha = score;
                        bestRow = availableMove[0];
                        bestCol = availableMove[1];
                        bestImmediateScore = immediateScore;
                        if (immediateScore >= WIN) {
                            mark(availableMove[0], availableMove[1], EMPTY);
                            toggleWhoseTurn();
                            break;
                        }
                    }
                } else {
                    score = minimax(maximizingPlayerMarker, alpha, beta)[0];
                    if (score <= beta) {
                        bestMoves.add(new int[]{-100, score, beta, availableMove[0], availableMove[1], bestImmediateScore, immediateScore});
                    }
                    //score += moves;
                    if (score < beta || (score <= beta && bestImmediateScore > immediateScore) || immediateScore <= -WIN) {
                        beta = score;
                        bestRow = availableMove[0];
                        bestCol = availableMove[1];
                        bestImmediateScore = immediateScore;
                        if (immediateScore <= -WIN) {
                            mark(availableMove[0], availableMove[1], EMPTY);
                            toggleWhoseTurn();
                            break;
                        }
                    }
                }

                mark(availableMove[0], availableMove[1], EMPTY);
                toggleWhoseTurn();

                if (alpha >= beta) break;
            }

            if (moves == currentMove) {
                System.out.println(moves + 1 + " " + maximizingPlayerMarker + " " + whoseTurn + " " + (whoseTurn == maximizingPlayerMarker));
                for (int[] bestMove : bestMoves) {
                    System.out.println(Arrays.toString(bestMove));
                }
            }

            return new int[] {(whoseTurn == maximizingPlayerMarker ? alpha : beta), bestRow, bestCol};
        }
    }
}
