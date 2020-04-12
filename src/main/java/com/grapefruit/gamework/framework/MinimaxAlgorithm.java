package com.grapefruit.gamework.framework;

import com.grapefruit.gamework.games.reversi.ReversiBoard;

import java.util.*;

import static com.grapefruit.gamework.games.reversi.ReversiFactory.STRATEGIC_VALUES;
import static java.lang.Integer.*;

public class MinimaxAlgorithm {
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

    public MinimaxAlgorithm(int depth) {
        currentDepth = depth;
    }

    public MinimaxAlgorithm(int depth, boolean dynamicDepth) {
        this(depth);
        this.dynamicDepth = dynamicDepth;
    }

    public void destroy() {
        if (timeoutThread != null) {
            timeoutThread.interrupt();
        }

        if (minimaxThreads != null) {
            for (Thread thread : minimaxThreads) thread.interrupt();
        }
    }

    public boolean isTimedOut() {
        return timedOut;
    }

    public Tile calculateBestMove(Board board, Player player, Player opponent, int turnCount) {
        this.turnCount = turnCount;
        timeoutStack = new Stack<>();
        timedOut = false;
//        revaluation(board);

        System.out.println("---------------- TURN " + turnCount + " -----------------");
        Tile tile = realCalculateBestMove(board, player, opponent, true, currentDepth);

        if (timeoutThread != null) {
            timeoutThread.interrupt();
        }

        return tile;
    }

    public static long getCurrentSeconds() {
        return System.currentTimeMillis() / 1000;
    }

    public long secondsLeft() {
        return (startTime + (timeout / 1000)) - getCurrentSeconds();
    }

    public void incrementDepth() {
        if (dynamicDepth) {
            System.out.println("increase depth");
            currentDepth++;
        }

    }

    public void decrementDepth() {
        if (dynamicDepth) {
            currentDepth--;
        }
    }

    public void turnCountDecrease() {
        if (currentDepth >= 9 && turnCount < 7) {
            currentDepth--;
        }
    }

    private Tile realCalculateBestMove(Board board, Player player, Player opponent, boolean firstTurn, int depth) {
        this.player = player;
        this.opponent = opponent;

        if (firstTurn && turnCount > 44) {
            System.out.println("turn > 44");
            incrementDepth();
        }

        if (firstTurn) {
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

        if (firstTurn && secondsLeft() > 8) {
            currentDepth++;
            System.out.println("increase depth");
        }

        if (!isTimedOut() && secondsLeft() >= 1 && depth < 30) {
            timeoutStack.push(isTimedOut());

            System.out.println("depth found: " + depth);
            int newDepth = depth + 1;
            Tile newTile = realCalculateBestMove(board, player, opponent, false, newDepth);
            System.out.println("New tile on depth: " + newDepth + " position: " + newTile.getRow() + ", " + newTile.getCol());
            if (!timeoutStack.isEmpty()) {
                System.out.println(timeoutStack);
                if (newTile != null && !timeoutStack.pop()) {
                    System.out.println("New best tile on depth: " + newDepth + " position: " + newTile.getRow() + ", " + newTile.getCol());
                    bestTile = newTile;
                    timeoutStack.clear();
                } else {
                    System.out.println("corrupt tile, ignoring");
                }
            }
        } else if (firstTurn && secondsLeft() <= 1) {
            System.out.println("decrease depth");
            currentDepth--;
        }

        return bestTile;
    }


    private Map<Tile, Integer> threadedMiniMax(Board board, Player player, int depth) {
        minimaxThreads = new ArrayList<>();
        List<Tile> moves = board.getAvailableMoves(player);
        Map<Tile, Integer> tiles = new HashMap<>();
        for (Tile tile : moves) {
            Board newBoard = new ReversiBoard(Board.BOARDSIZE, STRATEGIC_VALUES);
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

    public synchronized void triggerTimeout() {
        timedOut = true;
        timeoutStack.push(isTimedOut());
        System.out.println("TIMEOUTPUSHED");
    }

    public int minimax(int depth, Board board, int score, int alpha, int beta, boolean maximizingPlayer) {
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

            if (turnCount > 44) {
                return score + (board.countPieces(player) * 3);
//                return score + board.countPieces(player);
            } else {
                return score + board.countPieces(player);
            }
        }

        if (maximizingPlayer) {
            int maxScore = MIN_VALUE;
            List<Tile> moves = board.getAvailableMoves(player);

            if (moves.size() == 0) {
                return score;
            }

            for (Tile move : moves) {
                if (timedOut) break;

                Board newBoard = new ReversiBoard(Board.BOARDSIZE, STRATEGIC_VALUES);
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

            for (Tile move : moves) {
                if (timedOut) break;

                Board newBoard = new ReversiBoard(Board.BOARDSIZE, STRATEGIC_VALUES);
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

    public void revaluation(Board board) {
        Tile topleft = board.getTile(0, 0);
        Tile topright = board.getTile(0, board.getBoardSize() - 1);
        Tile bottomleft = board.getTile(board.getBoardSize() - 1, 0);
        Tile bottomright = board.getTile(board.getBoardSize() - 1, board.getBoardSize() - 1);

        if (topleft.getPlayer() != null && topright.getPlayer() != null && topleft.getStrategicValue() != 55) {
            System.out.println("Its damn time Right");
            board.changeValuesBetweenTiles(board.getTile(0, 0), "Right", 55);
        }

        if (topleft.getPlayer() != null && bottomleft.getPlayer() != null && topleft.getStrategicValue() != 55) {
            System.out.println("Its damn time Down");
            board.changeValuesBetweenTiles(board.getTile(0, 0), "Down", 55);
        }

        if (bottomright.getPlayer() != null && topright.getPlayer() != null && bottomright.getStrategicValue() != 55) {
            System.out.println("Its damn time Top");
            board.changeValuesBetweenTiles(board.getTile(board.getBoardSize() - 1, board.getBoardSize() - 1), "Top", 55);
        }

        if (bottomright.getPlayer() != null && bottomleft.getPlayer() != null && bottomright.getStrategicValue() != 55) {
            System.out.println("Its damn time Left");
            board.changeValuesBetweenTiles(board.getTile(board.getBoardSize() - 1, board.getBoardSize() - 1), "Left", 55);
        }
    }
}
