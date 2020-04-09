package com.grapefruit.gamework.framework;

import com.grapefruit.gamework.games.reversi.ReversiBoard;

import java.util.*;

import static com.grapefruit.gamework.games.reversi.ReversiFactory.STRATEGIC_VALUES;
import static java.lang.Integer.*;

public class MinimaxAlgorithm {
    private Player player;
    private Player opponent;
    ArrayList<Thread> threads;
    private int timeout;
    private boolean timedOut = false;
    private int currentDepth;
    private Thread timeoutThread;
    private long startTime;
    private Stack<Boolean> timeoutStack;
    boolean flag;

    public MinimaxAlgorithm(int depth) {
        currentDepth = depth;
    }

    public Tile calculateBestMove(Board board, Player player, Player opponent, int turnCount) {
        System.out.println("--------------------- TURN " + turnCount + " ---------------------");
        timedOut = false;
        timeoutThread = null;
        timeoutStack = new Stack<>();
        flag = false;
        return realCalculateBestMove(board, player, opponent, turnCount, true, currentDepth);
    }

    public static long getCurrentSeconds() {
        return System.currentTimeMillis() / 1000;
    }

    public long secondsLeft() {
        return (startTime + (timeout / 1000)) - getCurrentSeconds();
    }

    private Tile realCalculateBestMove(Board board, Player player, Player opponent, int turnCount, boolean firstTurn, int depth) {
        this.player = player;
        this.opponent = opponent;
        threads = new ArrayList<>();
        int alpha = MIN_VALUE;
        int beta = MAX_VALUE;

        Tile bestTile = null;

        if (firstTurn && turnCount > 44) {
            System.out.println("INCREASE BECAUSE OF HIGH TURN");
            currentDepth++;
        }

        System.out.println("Starting minimax with depth: " + depth);

        List<Tile> moves = board.getAvailableMoves(player);
        Map<Tile, Integer> tiles = new HashMap<>();
        for (Tile tile : moves) {
            Board newBoard = new ReversiBoard(board.BOARDSIZE, STRATEGIC_VALUES);
            newBoard.copyState(board);
            newBoard.setMove(tile.getRow(), tile.getCol(), player);
            Thread thread = new Thread(() -> {
                tiles.put(tile, minimax(
                        depth - 1,
                        newBoard,
                        tile.getStrategicValue(),
                        alpha,
                        beta,
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

        int bestScore = MIN_VALUE;
        for (Map.Entry<Tile, Integer> entry : tiles.entrySet()) {
            if (entry.getValue() > bestScore) {
//                if (player.getColor() == Colors.WHITE) {
//                    System.out.println(String.format("new best move: %s,%s - strategic value: %s, score: %s", entry.getKey().getRow(), entry.getKey().getCol(), entry.getKey().getStrategicValue(), entry.getValue()));
//                }
                bestTile = entry.getKey();
                bestScore = entry.getValue();
            } else if (entry.getKey().getStrategicValue() > bestTile.getStrategicValue() && entry.getValue() == bestScore) {
//                if (player.getColor() == Colors.WHITE) {
//                    System.out.println(String.format("new best move: %s,%s - strategic value: %s, score: %s", entry.getKey().getRow(), entry.getKey().getCol(), entry.getKey().getStrategicValue(), entry.getValue()));
//                }
                bestTile = entry.getKey();
                bestScore = entry.getValue();
            } else {
//                if (player.getColor() == Colors.WHITE) {
//                    System.out.println(String.format("not best move: %s,%s - strategic value: %s, score: %s", entry.getKey().getRow(), entry.getKey().getCol(), entry.getKey().getStrategicValue(), entry.getValue()));
//                }
            }
        }

        if (bestTile != null) {
            if (player.getColor() == Colors.WHITE) {
                //    System.out.println(String.format("best move: %s,%s - strategic value: %s, score: %s", bestTile.getRow(), bestTile.getCol(), bestTile.getStrategicValue(), bestScore));
            }
        }

        //System.out.println(tiles.size() + " | " + moves.size());

        System.out.println("timed out:" + timedOut);
        System.out.println("seconds left: " + secondsLeft());




        if (depth < 50) {
            if (!timedOut) {
                if (firstTurn && secondsLeft() >= 5) {
                    if (secondsLeft() >= 8) {
                        currentDepth++;
                    }

                    System.out.println("INCREASING DEPTH!!");
                    System.out.println("TRYING WITH RECURSION");

                    //Tile newTile = realCalculateBestMove(board, player, opponent, turnCount, false, depth + 1);

//                    if (!timedOut && newTile != null) {
//                        System.out.println("New best tile, YAY! DEO!");
//                        bestTile = newTile;
//                    } else {
//                        System.out.println("Corrupt tile, ignoring..");
//                    }
                }
//                if (!firstTurn && timedOut) {
//                    System.out.println("Corrupt tile, returning null..");
//                    return null;
//                }
            } else if (firstTurn && secondsLeft() <= 2) {
                System.out.println("DECREASING DEPTH!!");
                currentDepth--;
            }
        }

//        long endTime = getCurrentSeconds();
//        System.out.println("Timedout " + timedOut + " Time " + (endTime - startTime));
//        if (firstTurn && (endTime - startTime) <= 2) {
//            System.out.println("INCREASING DEPTH!!");
//            currentDepth++;
//            System.out.println("TRYING BETTER VALUE!");
//
//            Tile newTile = this.realCalculateBestMove(board, player, opponent, turnCount, false);
//
//            if(((System.currentTimeMillis() / 1000) - startTime) <= 8) {
//                System.out.println("LETS GO BETTER VALUE!");
//                if(newTile != null) {
//                    bestTile = newTile;
//                }
//            }
//
//        } else if(firstTurn && (endTime - startTime) >= 9) {
//            System.out.println("DECREASING DEPTH!!");
//            currentDepth--;
//        }

        if (timeoutThread != null) {
            timeoutThread.interrupt();
        }

        return bestTile;
    }

    public void startTimeout(int timeout) {
        System.out.println("START TIMEOUT");
        this.timeout = timeout;
        startTime = getCurrentSeconds();

        timeoutThread = new Thread(() -> {
            try {
                Thread.sleep(timeout);
                timedOut = true;
                System.out.println("STOP RECURSIONS!");
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
                Board newBoard = new ReversiBoard(board.BOARDSIZE, STRATEGIC_VALUES);
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

//                if (isInterrupted) break;
            }
            return maxScore;

        } else {
            int minScore = MAX_VALUE;
            List<Tile> moves = board.getAvailableMoves(opponent);

            if (moves.size() == 0) {
                return score;
            }

            for (Tile move : moves) {
                Board newBoard = new ReversiBoard(board.BOARDSIZE, STRATEGIC_VALUES);
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

//                if (isInterrupted) break;
            }
            return minScore;
        }
    }
}
