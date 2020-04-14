package com.grapefruit.gamework.games.reversi.AI;

import com.grapefruit.gamework.framework.AbstractMinimaxAlgorithm;
import com.grapefruit.gamework.framework.Board;
import com.grapefruit.gamework.framework.Player;
import com.grapefruit.gamework.framework.Tile;
import com.grapefruit.gamework.games.reversi.ReversiBoard;

import java.util.*;

import static com.grapefruit.gamework.games.reversi.ReversiFactory.STRATEGIC_VALUES;
import static java.lang.Integer.*;

public class newWinningAI extends AbstractMinimaxAlgorithm {
    private Player player;
    private Player opponent;
    private boolean timedOut = false;
    private int turnCount;

    public newWinningAI(int[][] strategicValues) {
        this(strategicValues, 9, true);
    }

    public newWinningAI(int[][] strategicValues, int depth, boolean dynamicDepth) {
        super(strategicValues, depth, dynamicDepth);
    }

    @Override
    public void setDepth(int depth) {
        currentDepth = depth;
    }

    public void setComplexity(int complexity) {
        super.setComplexity(complexity);
    }

    public Tile calculateBestMove(Board board, Player player, Player opponent, int turnCount) {
        this.turnCount = turnCount;
        timeoutStack = new Stack<>();
        timedOut = false;
        board.setStrategicValues(strategicValues);

        ((ReversiBoard) board).evaluateEdges(player);
        Tile tile = realCalculateBestMove(board, player, opponent, true, currentDepth);

        if (timeoutThread != null) {
            timeoutThread.interrupt();
        }

        return tile;
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
            } else if (bestTile != null && entry.getKey().getStrategicValue() > bestTile.getStrategicValue() &&
                    entry.getValue() == bestScore) {
                bestTile = entry.getKey();
                bestScore = entry.getValue();
            }
        }

        if (firstTurn && secondsLeft() > 8) {
            currentDepth++;
        }
        if (!isTimedOut() && secondsLeft() >= 1 && depth <= board.emptyTiles() && dynamicDepth) {
            timeoutStack.push(isTimedOut());
            System.out.println(timeoutStack);
            Tile newTile = realCalculateBestMove(board, player, opponent, false, depth + 1);
            if (!timeoutStack.isEmpty()) {
                if (newTile != null && !timeoutStack.pop()) {
                    bestTile = newTile;
                    timeoutStack.clear();
                }
            }
        } else if (firstTurn && secondsLeft() <= 1 && isTimedOut()) {
            currentDepth--;
        }

//        timedOut = false;
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

    public int minimax(int depth, ReversiBoard board, Tile currentMove, int score, int alpha, int beta, boolean maximizingPlayer) {
        if (isTimedOut()) {
            return score;
        }

        if (board.emptyTiles() <= 0) {
            if (maximizingPlayer) {
                int pieces = board.countPieces(player);
                if (pieces > 32) {
                    return score + 9999 + pieces;
                } else if (pieces == 32) {
                    return score + 100 + pieces;
                } else {
                    return score - 9999 - pieces;
                }
            }
        }

        if (depth == 0) {
            int combopoints = Helpers.comboPoints(board, currentMove, maximizingPlayer ? player : opponent);
            int edgepoints = Helpers.betweenCornerPoints(board, currentMove);
            int adjacentpoints = Helpers.adjacentValue(board, currentMove, maximizingPlayer ? opponent : player);
            int boardpieces = board.countPieces(player);

            if (maximizingPlayer) {
                score += combopoints + edgepoints + adjacentpoints;
                if (turnCount > 44) {
                    return score + boardpieces * 3;
                } else {
                    return score + boardpieces;
                }
            } else {
                score -= (combopoints + edgepoints + adjacentpoints);
                if (turnCount > 44) {
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
}
