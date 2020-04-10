package com.grapefruit.gamework.framework;

import com.grapefruit.gamework.games.reversi.ReversiBoard;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.grapefruit.gamework.games.reversi.ReversiFactory.STRATEGIC_VALUES;
import static java.lang.Integer.*;

public class MinimaxAlgorithm {
    private Player player;
    private Player opponent;
    private int timeout;

    public boolean isTimedOut() {
        return timedOut;
    }

    private boolean timedOut = false;
    private int currentDepth;
    private Thread timeoutThread;
    private long startTime;
    private Stack<Boolean> timeoutStack;

    public MinimaxAlgorithm(int depth) {
        currentDepth = depth;
    }

    public Tile calculateBestMove(Board board, Player player, Player opponent, int turnCount) {
        timeoutStack = new Stack<>();
        timedOut = false;
        System.out.println("---------------- TURN " + turnCount + " -----------------");

        Tile tile = realCalculateBestMove(board, player, opponent, turnCount, true, currentDepth);

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

    public void turnCountDecrease(int turn) {
        if (currentDepth >= 10 && turn == 2) {
            System.out.println("decrease depth (turn == 2)");
            currentDepth--;
        }
        if (currentDepth >= 9 && turn == 4) {
            System.out.println("decrease depth (turn == 4)");
            currentDepth--;
        }
    }

    private Tile realCalculateBestMove(Board board, Player player, Player opponent, int turnCount, boolean firstTurn, int depth) {
        this.player = player;
        this.opponent = opponent;

        if (turnCount > 44) {
            System.out.println("increase depth (turn > 44)");
            currentDepth++;
        }

        if (firstTurn) {
            turnCountDecrease(turnCount);
        }


        Map<Tile, Integer> tiles = threadedMiniMax(board, player, depth);

        Tile bestTile = null;
        int bestScore = MIN_VALUE;
        for (Map.Entry<Tile, Integer> entry : tiles.entrySet()) {
            if (entry.getValue() > bestScore) {
                bestTile = entry.getKey();
                bestScore = entry.getValue();

            } else if (bestTile != null &&
                            entry.getKey().getStrategicValue() > bestTile.getStrategicValue() &&
                            entry.getValue() == bestScore)
            {
                bestTile = entry.getKey();
                bestScore = entry.getValue();
            }
        }

        if (firstTurn && secondsLeft() >= 8) {
            currentDepth++;
            System.out.println("increase depth");
        }

        if(!isTimedOut() && secondsLeft() >= 1 && depth < 30) {
            timeoutStack.push(isTimedOut());

            System.out.println("depth: " + depth);
            Tile newTile = realCalculateBestMove(board, player, opponent, turnCount, false, depth + 1);
            System.out.println(timeoutStack);
            if(!timeoutStack.isEmpty()) {
                if (newTile != null && !timeoutStack.pop()) {
                    System.out.println("new best tile on depth " + depth);
                    bestTile = newTile;
                    timeoutStack.clear();
                } else {
                    System.out.println("corrupt tile, ignoring");
                }
            }
        }

        if (firstTurn && secondsLeft() <= 1) {
            System.out.println("decrease depth");
            currentDepth--;
        }

        return bestTile;
    }


    private Map<Tile, Integer> threadedMiniMax(Board board, Player player, int depth) {
        ArrayList<Thread> threads = new ArrayList<>();
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
            threads.add(thread);
        }

        for (Thread thread : threads) {
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
                timedOut = true;
                timeoutStack.push(isTimedOut());
                System.out.println("TIMEOUTPUSHED");
                System.out.println("TIMEOUTPUSHED");
            } catch (InterruptedException ignored) {
            }
        });

        timeoutThread.start();
    }

    public int minimax(int depth, Board board, int score, int alpha, int beta, boolean maximizingPlayer) {
        if (depth == 0 || timedOut) {
            return score + board.countPieces(player);
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
}
