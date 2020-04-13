package com.grapefruit.gamework.games.reversi.AI;

import com.grapefruit.gamework.framework.Board;
import com.grapefruit.gamework.framework.MinimaxAlgorithm;
import com.grapefruit.gamework.framework.Player;
import com.grapefruit.gamework.framework.Tile;
import com.grapefruit.gamework.games.reversi.ReversiBoard;

import java.util.*;

import static java.lang.Integer.*;

public class JarnoAI implements MinimaxAlgorithm {
    private Board boardIn;
    private boolean dynamicDepth = true;
    private Player player;
    private Player opponent;
    private int turnCount;
    private int timeout;
    private long startTime;
    private boolean timedOut = false;
    private int currentDepth;
    private Thread timeoutThread;
    private Stack<Boolean> timeoutStack;
    ArrayList<Thread> minimaxThreads;
    private int[][] strategicValues = getStrategicValues();
    private static int strategicValueFactor = 1;

    public JarnoAI() {
        this(7, false);
    }

    public JarnoAI(int depth, boolean dynamicDepth) {
        this(depth);
        this.dynamicDepth = dynamicDepth;
    }

    public JarnoAI(int depth) {
        currentDepth = depth;
    }

    public void destroy() {
        if (timeoutThread != null) {
            timeoutThread.interrupt();
        }

        if (minimaxThreads != null) {
            for (Thread thread : minimaxThreads) thread.interrupt();
        }
    }

    private boolean isTimedOut() {
        return timedOut;
    }

    public void startTimeout(int timeout) {
        this.timeout = timeout;
        startTime = getCurrentSeconds();

        timeoutThread = new Thread(() -> {
            try {
                Thread.sleep(timeout);
                triggerTimeout();

            } catch (InterruptedException ignored) {
            }
        });

        timeoutThread.start();
    }

    public Tile calculateBestMove(Board boardIn, Player player, Player opponent, int turnCount) {
        this.boardIn = boardIn;
        this.turnCount = turnCount;
        timeoutStack = new Stack<>();
        timedOut = false;

        boardIn.setStrategicValues(strategicValues);

        ReversiBoard board = new ReversiBoard(boardIn.getBoardSize(), strategicValues);
        board.copyState(boardIn);

//        revaluation(board);

        wedgeStrategy(board, board.getAvailableMoves(player));


//        List<Tile> topEdge = edges.get("top");
//        if (topEdge.size() > 0) {
//            // Even
//            if (topEdge.size() % 2 == 0) {
//                for (Tile tile : topEdge) {
//                    board.setStrategicValue(tile.getRow(), tile.getCol(), 24 * strategicValueFactor);
//                }
//            // Odd
//            } else {
//                for (Tile tile : topEdge) {
//                    board.setStrategicValue(tile.getRow(), tile.getCol(), -8 * strategicValueFactor);
//                }
//            }
//        }

//        stableDiscStrategy(board);
        board.printStrategicValues();

//        List<Tile> moves = board.getAvailableMoves(player);
//        for (Tile move : moves) {
//            if (isCorner(board, move)) {
//                System.out.println("Is corner!");
//                return move;
//            }
//        }

        System.out.println("---------------- TURN " + turnCount + " -----------------");
        Tile tile = realCalculateBestMove(board, player, opponent, true, currentDepth);

        if (timeoutThread != null) {
            timeoutThread.interrupt();
        }

        return tile;
    }

    private static long getCurrentSeconds() {
        return System.currentTimeMillis() / 1000;
    }

    private long secondsLeft() {
        return (startTime + (timeout / 1000)) - getCurrentSeconds();
    }

    private void incrementDepth() {
        if (dynamicDepth) {
            System.out.println("increase depth");
            currentDepth++;
        }

    }

    private void decrementDepth() {
        if (dynamicDepth) {
            System.out.println("decrease depth");
            currentDepth--;
        }
    }

    private void turnCountDecrease() {
        if (currentDepth >= 9 && turnCount < 7) {
            decrementDepth();
        }
    }

    private Tile realCalculateBestMove(Board board, Player player, Player opponent, boolean firstTurn, int depth) {
        this.player = player;
        this.opponent = opponent;

        if (dynamicDepth && firstTurn && turnCount > 44) {
            System.out.println("turn > 44");
            incrementDepth();
        }

        if (dynamicDepth && firstTurn) {
            turnCountDecrease();
        }

        System.out.println("start minimax for depth: " + depth);
        Map<Tile, Integer> tiles = threadedMiniMax(board, player, depth);

        Tile bestTile = null;
        int bestScore = MIN_VALUE;
        for (Map.Entry<Tile, Integer> entry : tiles.entrySet()) {
            if (entry.getValue() > bestScore) {
                bestTile = entry.getKey();
                bestScore = entry.getValue();

            } else if (bestTile != null &&
                    entry.getKey().getStrategicValue() > bestTile.getStrategicValue() &&
                    entry.getValue() == bestScore) {
                bestTile = entry.getKey();
                bestScore = entry.getValue();
            }
        }

        if (dynamicDepth) {
            if (firstTurn && secondsLeft() > 8) {
                incrementDepth();
            }

            if (firstTurn && !isTimedOut() && secondsLeft() >= 1 && depth < 15) {
                timeoutStack.push(isTimedOut());

                System.out.println("depth found: " + depth);
                int newDepth = depth + 1;

                ReversiBoard newBoard = new ReversiBoard(board.getBoardSize(), strategicValues);
                newBoard.copyState(board);

                Tile newTile = realCalculateBestMove(newBoard, player, opponent, false, newDepth);
                System.out.println("New tile on depth: " + newDepth + " position: " + newTile.getRow() + ", " + newTile.getCol());
                if (!timeoutStack.isEmpty()) {
                    System.out.println(timeoutStack);
                    try {
                        boolean timeout = timeoutStack.pop();

                        if (newTile != null && !timeout) {
                            System.out.println("New best tile on depth: " + newDepth + " position: " + newTile.getRow() + ", " + newTile.getCol());
                            bestTile = newTile;
                            timeoutStack.clear();
                        } else {
                            System.out.println("corrupt tile, ignoring");
                        }
                    } catch (EmptyStackException ignored) {
                    }
                }
            } else if (firstTurn && secondsLeft() <= 1) {
                decrementDepth();
            }
        }

        return bestTile;
    }


    private Map<Tile, Integer> threadedMiniMax(Board board, Player player, int depth) {
        minimaxThreads = new ArrayList<>();
        List<Tile> moves = board.getAvailableMoves(player);

        Map<Tile, Integer> tiles = new HashMap<>();
        for (Tile tile : moves) {
            Board newBoard = new ReversiBoard(board.getBoardSize(), strategicValues);
            newBoard.copyState(board);
            newBoard.setMove(tile.getRow(), tile.getCol(), player);
            Thread thread = new Thread(() -> {
                tiles.put(tile, minimax(
                        depth - 1,
                        newBoard,
                        tile.getStrategicValue(),
                        MIN_VALUE,
                        MAX_VALUE,
                        false
                ));
            });
            thread.start();
            minimaxThreads.add(thread);
        }

        for (Thread thread : minimaxThreads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return tiles;
    }

    private synchronized void triggerTimeout() {
        timedOut = true;
        timeoutStack.push(isTimedOut());
        System.out.println("TIMEOUTPUSHED");
    }

    private int minimax(int depth, Board board, int score, int alpha, int beta, boolean maximizingPlayer) {
//        stableDiscStrategy((ReversiBoard) board);

        if (depth == 0 || timedOut) {
//            if (turnCount > 45) {
//                if (!board.anyMovesLeft(new Player[]{player, opponent})) {
//                    System.out.println("End game predicted");
//                    if (board.countPieces(player) > 32) {
//                        System.out.println("We won, returning 9999");
//                        return 9999;
//                    } else {
//                        System.out.println("We lost");
//                        return -99;
//                    }
//                }
//            }

//            if (turnCount > 46) {
//                if (!board.anyMovesLeft(new Player[]{player, opponent})) {
//                    if (board.countPieces(player) > 48) {
//                        System.out.println("Count pieces significantly higer than opponent");
//                        return 120;
//                    }
//                }
//            }

            if (turnCount > 48) {
                return score + (board.countPieces(player) * 40);
//                return score + board.countPieces(player);
//                return score;
            } else {
                return score + board.countPieces(player);
//                return score;
            }
        }

        if (maximizingPlayer) {
            int maxScore = MIN_VALUE;
            List<Tile> moves = board.getAvailableMoves(player);

            if (moves.size() == 0) {
                return score;
            }

//            wedgeStrategy((ReversiBoard) board, moves);

            for (Tile move : moves) {
                if (timedOut) break;

                Board newBoard = new ReversiBoard(board.getBoardSize(), strategicValues);
                newBoard.copyState(board);
                newBoard.setMove(move.getRow(), move.getCol(), player);

                int currentScore = minimax(
                        depth - 1,
                        newBoard,
                        score + move.getStrategicValue(),
                        alpha, beta,
                        false
                );

                maxScore = max(maxScore, currentScore);

                alpha = max(alpha, currentScore);
                if (beta <= alpha) {
                    break;
                }

                if (move.getStrategicValue() == 99) {
                    break;
                }

            }
            return maxScore;

        } else {
            int minScore = MAX_VALUE;
            List<Tile> moves = board.getAvailableMoves(opponent);

            if (moves.size() == 0) {
                return score;
            }

//            wedgeStrategy((ReversiBoard) board, moves);

            for (Tile move : moves) {
                if (timedOut) break;

                Board newBoard = new ReversiBoard(board.getBoardSize(), strategicValues);
                newBoard.copyState(board);
                newBoard.setMove(move.getRow(), move.getCol(), opponent);

                int currentScore = minimax(
                        depth - 1,
                        newBoard,
                        score - move.getStrategicValue(),
                        alpha,
                        beta,
                        true
                );

                minScore = min(minScore, currentScore);

                beta = min(beta, currentScore);
                if (beta <= alpha) {
                    break;
                }
                if (move.getStrategicValue() == 99) {
                    break;
                }

            }
            return minScore;
        }
    }

    private void revaluation(Board board) {
        Tile topleft = board.getTile(0, 0);
        Tile topright = board.getTile(0, board.getBoardSize() - 1);
        Tile bottomleft = board.getTile(board.getBoardSize() - 1, 0);
        Tile bottomright = board.getTile(board.getBoardSize() - 1, board.getBoardSize() - 1);

        int value = 55 * strategicValueFactor;
//        int value = 3 * 40;

        if (topleft.getPlayer() != null && topright.getPlayer() != null && topleft.getStrategicValue() != value) {
            System.out.println("Its damn time Right");
            board.changeValuesBetweenTiles(board.getTile(0, 0), "Right", value);
        }

        if (topleft.getPlayer() != null && bottomleft.getPlayer() != null && topleft.getStrategicValue() != value) {
            System.out.println("Its damn time Down");
            board.changeValuesBetweenTiles(board.getTile(0, 0), "Down", value);
        }

        if (bottomright.getPlayer() != null && topright.getPlayer() != null && bottomright.getStrategicValue() != value) {
            System.out.println("Its damn time Top");
            board.changeValuesBetweenTiles(board.getTile(board.getBoardSize() - 1, board.getBoardSize() - 1), "Top", value);
        }

        if (bottomright.getPlayer() != null && bottomleft.getPlayer() != null && bottomright.getStrategicValue() != value) {
            System.out.println("Its damn time Left");
            board.changeValuesBetweenTiles(board.getTile(board.getBoardSize() - 1, board.getBoardSize() - 1), "Left", value);
        }
    }

    private void stableDiscStrategy(ReversiBoard board) {
        Tile topleft = board.getTile(0, 0);
        Tile topright = board.getTile(0, board.getBoardSize() - 1);
        Tile bottomleft = board.getTile(board.getBoardSize() - 1, 0);
        Tile bottomright = board.getTile(board.getBoardSize() - 1, board.getBoardSize() - 1);

        if (topleft.getPlayer() != null) {
            maxStrategicValueOfNeighbours(board, topleft, new ArrayList<>());
        }
        if (topright.getPlayer() != null) {
            maxStrategicValueOfNeighbours(board, topright, new ArrayList<>());
        }
        if (bottomleft.getPlayer() != null) {
            maxStrategicValueOfNeighbours(board, bottomleft, new ArrayList<>());
        }
        if (bottomright.getPlayer() != null) {
            maxStrategicValueOfNeighbours(board, bottomright, new ArrayList<>());
        }
    }

    private void maxStrategicValueOfNeighbours(ReversiBoard board, Tile tile, List<Tile> visited) {
        HashSet<Tile> neighbours = board.getDirectNeighbours(tile);

        if (neighbours.size() < 1) return;

        for (Tile neighbour : neighbours) {
            if (visited.contains(neighbour)) continue;

            visited.add(neighbour);

            if (neighbour.getPlayer() == null) {
                board.setStrategicValue(neighbour.getRow(), neighbour.getCol(), 45 * strategicValueFactor);
            } else if (neighbour.getPlayer() == tile.getPlayer()) {
                maxStrategicValueOfNeighbours(board, neighbour, visited);
            }
        }
    }

    private void wedgeStrategy(ReversiBoard board, List<Tile> moves) {
        /* Wedge strategy */
        Map<String, List<Tile>> edges = getEdges(board, moves);
        for (Map.Entry<String, List<Tile>> entry : edges.entrySet()) {
            List<Tile> edge = entry.getValue();
            if (edge.size() > 0) {
                System.out.println("Performing wedge strategy");

                // Even
                if (edge.size() % 2 == 0) {
                    System.out.println("wedge strategy increase edge: " + entry.getKey());
                    for (Tile tile : edge) {
                        board.setStrategicValue(tile.getRow(), tile.getCol(), tile.getStrategicValue() + 5 * strategicValueFactor);
                    }
                    // Odd
                } else {
                    System.out.println("wedge strategy decrease edge: " + entry.getKey());
                    for (Tile tile : edge) {
                        board.setStrategicValue(tile.getRow(), tile.getCol(), tile.getStrategicValue() - 5 * strategicValueFactor);
                    }
                }
            }
        }
        /* End wedge strategy */
    }

    private boolean isCorner(Board board, Tile tile) {
        if (tile.getRow() == 0 && tile.getCol() == 0) {
            return true;
        } else if (tile.getRow() == 0 && tile.getCol() == board.getBoardSize() - 1) {
            return true;
        } else if (tile.getRow() == board.getBoardSize() - 1 && tile.getCol() == 0) {
            return true;
        } else if (tile.getRow() == board.getBoardSize() - 1 && tile.getCol() == board.getBoardSize() - 1) {
            return true;
        }
        return false;
    }

    private boolean isEdge(Board board, Tile tile) {
        return tile.getRow() == 0 || tile.getRow() == board.getBoardSize() - 1 ||
                tile.getCol() == 0 || tile.getCol() == board.getBoardSize() - 1;
    }

    private Map<String, List<Tile>> getEdges(Board board, List<Tile> tiles) {
        List<Tile> topEdge = new ArrayList<>();
        List<Tile> bottomEdge = new ArrayList<>();
        List<Tile> leftEdge = new ArrayList<>();
        List<Tile> rightEdge = new ArrayList<>();

        for (Tile tile : tiles) {
            if (tile.getRow() == 0) {
                topEdge.add(tile);
            } else if (tile.getRow() == board.getBoardSize() - 1) {
                bottomEdge.add(tile);
            } else if (tile.getCol() == 0) {
                leftEdge.add(tile);
            }  else if (tile.getCol() == board.getBoardSize() - 1) {
                rightEdge.add(tile);
            }
        }

        Map<String, List<Tile>> map = new HashMap<String, List<Tile>>();
        map.put("top", topEdge);
        map.put("bottom", bottomEdge);
        map.put("left", leftEdge);
        map.put("right", rightEdge);

        return map;
    }


    private static int[][] getStrategicValues() {
        int[][] strat = new int[8][8];

        strat[0][0] = 99;
        strat[0][1] = -8;
        strat[0][2] = 8;
        strat[0][3] = 6;
        strat[0][4] = 6;
        strat[0][5] = 8;
        strat[0][6] = -8;
        strat[0][7] = 99;

        strat[1][0] = -8;
        strat[1][1] = -24;
        strat[1][2] = -4;
        strat[1][3] = -3;
        strat[1][4] = -3;
        strat[1][5] = -4;
        strat[1][6] = -24;
        strat[1][7] = -8;

        strat[2][0] = 8;
        strat[2][1] = -4;
        strat[2][2] = 7;
        strat[2][3] = 4;
        strat[2][4] = 4;
        strat[2][5] = 7;
        strat[2][6] = -4;
        strat[2][7] = 8;

        strat[3][0] = 6;
        strat[3][1] = -3;
        strat[3][2] = 4;
        strat[3][3] = 0;
        strat[3][4] = 0;
        strat[3][5] = 4;
        strat[3][6] = -3;
        strat[3][7] = 6;

        strat[4][0] = 6;
        strat[4][1] = -3;
        strat[4][2] = 4;
        strat[4][3] = 0;
        strat[4][4] = 0;
        strat[4][5] = 4;
        strat[4][6] = -3;
        strat[4][7] = 6;

        strat[5][0] = 8;
        strat[5][1] = -4;
        strat[5][2] = 7;
        strat[5][3] = 4;
        strat[5][4] = 4;
        strat[5][5] = 7;
        strat[5][6] = -4;
        strat[5][7] = 8;

        strat[6][0] = -8;
        strat[6][1] = -24;
        strat[6][2] = -4;
        strat[6][3] = -3;
        strat[6][4] = -3;
        strat[6][5] = -4;
        strat[6][6] = -24;
        strat[6][7] = -8;

        strat[7][0] = 99;
        strat[7][1] = -8;
        strat[7][2] = 8;
        strat[7][3] = 6;
        strat[7][4] = 6;
        strat[7][5] = 8;
        strat[7][6] = -8;
        strat[7][7] = 99;

        for (int i = 0; i < strat.length; i++) {
            for (int j = 0; j < strat.length; j++) {
                strat[i][j] = strat[i][j] * strategicValueFactor;
            }
        }
        return strat;
    }
}
