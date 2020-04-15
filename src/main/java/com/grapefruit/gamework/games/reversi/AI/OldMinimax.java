package com.grapefruit.gamework.games.reversi.AI;

import com.grapefruit.gamework.framework.*;
import com.grapefruit.gamework.games.reversi.ReversiBoard;

import java.util.*;

import static java.lang.Integer.*;

/**
 * The type Old minimax.
 */
public class OldMinimax extends AbstractMinimax {
    private Player player;
    private Player opponent;
    private boolean timedOut = false;

    /**
     * Instantiates a new Old minimax.
     *
     * @param strategicValues the strategic values
     */
    public OldMinimax(int[][] strategicValues) {
        super(strategicValues, 7, false);
    }

    /**
     * Instantiates a new Old minimax.
     *
     * @param strategicValues the strategic values
     * @param depth           the depth
     */
    public OldMinimax(int[][] strategicValues, int depth) {
        super(strategicValues, depth, false);
    }

    /**
     * Sets dynamic depth.
     *
     * @param dynamicDepth the dynamic depth
     */
    public void setDynamicDepth(boolean dynamicDepth) {
        this.dynamicDepth = dynamicDepth;
    }

    public void setComplexity(int complexity) {
        super.setComplexity(complexity);
    }

    public Tile calculateBestMove(Board board, Player player, Player opponent, int turnCount) {
        timedOut = false;
        timeoutThread = null;
        timeoutStack = new Stack<>();
        currentDepth = 7;
        board.setStrategicValues(strategicValues);
        return realCalculateBestMove(board, player, opponent, turnCount, true, currentDepth);
    }

    @Override
    public void setDepth(int depth) {
        currentDepth = depth;
    }

    /**
     * Sets ai complexity.
     *
     * @param complexity the complexity
     */
    public static void setAIComplexity(int complexity) {
    }

    private Tile realCalculateBestMove(Board board, Player player, Player opponent, int turnCount, boolean firstTurn, int depth) {
        this.player = player;
        this.opponent = opponent;
        threads = new ArrayList<>();
        int alpha = MIN_VALUE;
        int beta = MAX_VALUE;

        Tile bestTile = null;

        if (firstTurn && turnCount > 44 && dynamicDepth) {
            currentDepth++;
        }

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
                bestTile = entry.getKey();
                bestScore = entry.getValue();
            } else if (bestTile != null && entry.getKey().getStrategicValue() > bestTile.getStrategicValue() && entry.getValue() == bestScore) {
                bestTile = entry.getKey();
                bestScore = entry.getValue();
            }
        }

        if (depth < 50) {
            if (!timedOut) {
                if (firstTurn && secondsLeft() >= 3) {
                    Tile newTile = realCalculateBestMove(board, player, opponent, turnCount, false, depth + 1);
                    if (!timedOut && newTile != null) {
                        bestTile = newTile;
                    }
                }
            } else if (firstTurn && secondsLeft() <= 1) {
                currentDepth--;
            }
        }

        if (timeoutThread != null) {
            timeoutThread.interrupt();
        }

        timedOut = false;
        return bestTile;
    }


    /**
     * Minimax int.
     *
     * @param depth            the depth
     * @param board            the board
     * @param score            the score
     * @param alpha            the alpha
     * @param beta             the beta
     * @param maximizingPlayer the maximizing player
     * @return the int
     */
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
            }
            return minScore;
        }
    }
}
