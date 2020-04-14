package com.grapefruit.gamework.games.reversi.AI;

import com.grapefruit.gamework.framework.Board;
import com.grapefruit.gamework.framework.MinimaxAlgorithm;
import com.grapefruit.gamework.framework.Player;
import com.grapefruit.gamework.framework.Tile;
import com.grapefruit.gamework.games.reversi.Reversi;
import com.grapefruit.gamework.games.reversi.ReversiBoard;
import java.util.*;

import static com.grapefruit.gamework.games.reversi.ReversiFactory.STRATEGIC_VALUES;
import static java.lang.Integer.*;

public class DelanoAI implements MinimaxAlgorithm {
    private Player player;
    private Player opponent;
    private int timeout;
    private boolean timedOut = false;
    private int currentDepth;
    private Thread timeoutThread;
    private long startTime;
    private Stack<Boolean> timeoutStack;
    private int turnCount;
    ArrayList<Thread> threads;
    private boolean dynamicDepth = true;
    private int[][] strategicValues = getStrategicValues();
    private boolean winningCondition = false;

    public DelanoAI() {
        this(9, true);
    }

    public DelanoAI(int depth, boolean dynamicDepth) {
        currentDepth = depth;
        this.dynamicDepth = dynamicDepth;
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
    public boolean isTimedOut() {
        return timedOut;
    }

    public Tile calculateBestMove(Board board, Player player, Player opponent, int turnCount) {
        this.turnCount = turnCount;
        timeoutStack = new Stack<>();
        timedOut = false;
        board.setStrategicValues(strategicValues);
        //revaluation(board);

        ((ReversiBoard) board).evaluateEdges(player);
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

    public void turnCountDecrease(int depth) {
        if (currentDepth >= 8 && turnCount < 7) {
            currentDepth--;
        }

        if (turnCount > 41) {
            currentDepth++;
        }

        if (turnCount > 20 && turnCount < 31 && depth >= 8) {
            currentDepth--;
        }

        if (turnCount >= 31 && depth < 8) {
            currentDepth++;
        }
    }

    private Tile realCalculateBestMove(Board board, Player player, Player opponent, boolean firstTurn, int depth) {
        this.player = player;
        this.opponent = opponent;

        if (firstTurn) {
            turnCountDecrease(depth);
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

        if (firstTurn && secondsLeft() > 8) {
            currentDepth++;
            System.out.println("increase depth");
        }

        if(!isTimedOut() && secondsLeft() >= 1 && depth <= board.emptyTiles() && dynamicDepth) {
            timeoutStack.push(isTimedOut());

            System.out.println("depth: " + depth);
            Tile newTile = realCalculateBestMove(board, player, opponent, false, depth + 1);
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
        } else if (firstTurn && secondsLeft() <= 1 && isTimedOut()) {
            System.out.println("decrease depth");
            currentDepth--;
        }

        return bestTile;
    }


    Map<Tile, Integer> tiles;
    private Map<Tile, Integer> threadedMiniMax(Board board, Player player, int depth) {
        threads = new ArrayList<>();
        List<Tile> moves = board.getAvailableMoves(player);
        tiles = new HashMap<>();
        for (Tile tile : moves) {
            ReversiBoard newBoard = new ReversiBoard(board.getBoardSize(), STRATEGIC_VALUES);
            newBoard.copyState(board);
            newBoard.setMove(tile.getRow(), tile.getCol(), player);
            Thread thread = new Thread(() -> {
                tiles.put(tile, minimax(
                        depth - 1,
                        newBoard,
                        tile,
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
                triggerTimeout();
            } catch (InterruptedException ignored) {
            }
        });

        timeoutThread.start();
    }

    public synchronized void triggerTimeout() {
        System.out.println("Trigger Timeout!");
        timedOut = true;
        timeoutStack.push(true);
    }


    public int minimax(int depth, ReversiBoard board, Tile currentMove, int score, int alpha, int beta, boolean maximizingPlayer) {
        if(board.emptyTiles() <= 0) {
            if (maximizingPlayer) {
                int pieces =  board.countPieces(player);
                if(pieces > 32) {
                    return score + 9999 + pieces;
                } else if(pieces == 32) {
                    return score + 100 + pieces;
                } else {
                    return score - 9999 - pieces;
                }
            }
        }

        if (depth == 0 || timedOut) {
            int combopoints = Helpers.comboPoints(board, currentMove, maximizingPlayer ? player : opponent);
            int edgepoints = Helpers.betweenCornerPoints(board, currentMove);
            int adjacentpoints = Helpers.adjacentValue(board, currentMove, maximizingPlayer ? opponent : player);
            int boardpieces = board.countPieces(player);

            if (maximizingPlayer) {
                score += combopoints + edgepoints + adjacentpoints;
                if(turnCount > 44) {
                    return score + boardpieces * 3;
                } else {
                    return score + boardpieces;
                }
            } else {
                score -= (combopoints + edgepoints + adjacentpoints);
                if(turnCount > 44) {
                    return score - boardpieces * 3;
                } else {
                    return score - boardpieces;
                }
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

                ReversiBoard newBoard = new ReversiBoard(board.getBoardSize(), STRATEGIC_VALUES);
                newBoard.copyState(board);
                newBoard.setMove(move.getRow(), move.getCol(), player);

                int currentScore = minimax(
                        depth - 1,
                        newBoard,
                        move,
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

                ReversiBoard newBoard = new ReversiBoard(board.getBoardSize(), STRATEGIC_VALUES);
                newBoard.copyState(board);
                newBoard.setMove(move.getRow(), move.getCol(), opponent);

                int currentScore = minimax(
                        depth - 1,
                        newBoard,
                        move,
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
