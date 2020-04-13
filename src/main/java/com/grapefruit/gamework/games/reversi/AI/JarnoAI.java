package com.grapefruit.gamework.games.reversi.AI;

import com.grapefruit.gamework.framework.Board;
import com.grapefruit.gamework.framework.MinimaxAlgorithm;
import com.grapefruit.gamework.framework.Player;
import com.grapefruit.gamework.framework.Tile;
import com.grapefruit.gamework.games.reversi.ReversiBoard;

import java.util.*;

import static com.grapefruit.gamework.games.reversi.ReversiFactory.STRATEGIC_VALUES;
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


        ReversiBoard board = new ReversiBoard(boardIn.getBoardSize(), STRATEGIC_VALUES);
        board.copyState(boardIn);

        revaluation(board);

//        stableDiscStrategy(board);
//        board.printStrategicValues();

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

                ReversiBoard newBoard = new ReversiBoard(board.getBoardSize(), STRATEGIC_VALUES);
                newBoard.copyState(boardIn);

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
            Board newBoard = new ReversiBoard(board.getBoardSize(), STRATEGIC_VALUES);
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

            for (Tile move : moves) {
                if (timedOut) break;

                Board newBoard = new ReversiBoard(board.getBoardSize(), STRATEGIC_VALUES);
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

                Board newBoard = new ReversiBoard(board.getBoardSize(), STRATEGIC_VALUES);
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

        int value = 55 * 40;
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
                board.setStrategicValue(neighbour.getRow(), neighbour.getCol(), 45 * 40);
            } else if (neighbour.getPlayer() == tile.getPlayer()) {
                maxStrategicValueOfNeighbours(board, neighbour, visited);
            }
        }
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
}
