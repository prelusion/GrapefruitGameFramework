package com.grapefruit.gamework.games.reversi.AI;

import com.grapefruit.gamework.framework.*;
import com.grapefruit.gamework.games.reversi.ReversiBoard;

import java.util.*;

import static java.lang.Integer.*;

public class LeonAI implements MinimaxAlgorithm {
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
    private int[][] strategicValues = getStrategicValues();

    public LeonAI() {
        currentDepth = 7;
    }

    public LeonAI(int depth) {
        currentDepth = depth;
    }

    public void destroy() {
        if (timeoutThread != null) {
            timeoutThread.interrupt();
        }

        if (threads != null) {
            for (Thread thread : threads) {
                thread.interrupt();
            }
        }

    }

    public Tile calculateBestMove(Board board, Player player, Player opponent, int turnCount) {
        System.out.println("--------------------- TURN " + turnCount + " ---------------------");
        timedOut = false;
        timeoutThread = null;
        timeoutStack = new Stack<>();
        flag = false;
        currentDepth = 7;
        board.setStrategicValues(strategicValues);

        revaluation(board);

        return realCalculateBestMove(board, player, opponent, turnCount, true, currentDepth);
    }

    public void revaluation(Board board) {
        Tile topleft = board.getTile(0, 0);
        Tile topright = board.getTile(0, board.getBoardSize() - 1);
        Tile bottomleft = board.getTile(board.getBoardSize() - 1, 0);
        Tile bottomright = board.getTile(board.getBoardSize() - 1, board.getBoardSize() - 1);
        if(topright.getPlayer() != null) {
            if(topleft.getPlayer() != null && topleft.getStrategicValue() != 55) {
                System.out.println("Its damn time Right");
                board.changeValuesBetweenTiles(board.getTile(0, 0), "Right", 55);
                return;
            }
        }

        if(topleft.getPlayer() != null) {
            if(bottomleft.getPlayer() != null && topleft.getStrategicValue() != 55) {
                System.out.println("Its damn time Down");
                board.changeValuesBetweenTiles(board.getTile(0, 0), "Down", 55);
                return;
            }
        }

        if(bottomright.getPlayer() != null) {
            if(topright.getPlayer() != null && bottomright.getStrategicValue() != 55) {
                System.out.println("Its damn time Top");
                board.changeValuesBetweenTiles(board.getTile(board.getBoardSize() - 1, board.getBoardSize() - 1), "Top", 55);
                return;
            }
        }

        if(bottomleft.getPlayer() != null) {
            if (bottomright.getPlayer() != null && bottomright.getStrategicValue() != 55) {
                System.out.println("Its damn time Left");
                board.changeValuesBetweenTiles(board.getTile(board.getBoardSize() - 1, board.getBoardSize() - 1), "Left", 55);
                return;
            }
        }
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
            Board newBoard = new ReversiBoard(board.getBoardSize(), strategicValues);
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
                if (firstTurn && secondsLeft() >= 3) {
                    if (secondsLeft() >= 4) {
//                        currentDepth++;
                    }

                    System.out.println("INCREASING DEPTH!!");
                    System.out.println("TRYING WITH RECURSION");

                    Tile newTile = realCalculateBestMove(board, player, opponent, turnCount, false, depth + 1);

                    if (!timedOut && newTile != null) {
                        System.out.println("New best tile, YAY! DEO!");
                        bestTile = newTile;
                    } else {
                        System.out.println("Corrupt tile, ignoring..");
                    }
                }
//                if (!firstTurn && timedOut) {
//                    System.out.println("Corrupt tile, returning null..");
//                    return null;
//                }
            } else if (firstTurn && secondsLeft() <= 1) {
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

//                if (isInterrupted) break;
            }
            return minScore;
        }
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

        return strat;
    }
}
