package com.grapefruit.gamework.framework;

import com.grapefruit.gamework.framework.player.AIPlayer;
import com.grapefruit.gamework.framework.player.Player;
import com.grapefruit.gamework.games.reversi.ReversiBoard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.max;
import static java.lang.Integer.min;

public class MinimaxAlgorithm implements MoveAlgorithm {

    public MinimaxAlgorithm() {
    }

    @Override
    public int minimax(int depth, Board board, Tile tile, int alpha, int beta,
                       Player player, Player opponentPlayer, boolean maximizingPlayer) {
        return 0;
    }

    @Override
    public Tile calculateBestMove(Board board, Player player, Player opponent, int depth) {
        int alpha = -999999;
        int beta = +999999;
        int maxScore = -9999999;
        Tile bestTile = null;

        List<Tile> moves = board.getAvailableMoves(player);
        Map<Tile, Integer> tiles = new HashMap<>();
        for (Tile tile : moves) {
            Board newBoard = new ReversiBoard(board.boardSize);
            newBoard.copyState(board);
            newBoard.setMove(tile.getRow(), tile.getCol(), player);

            tiles.put(tile, minimax2(
                    depth - 1,
                    newBoard,
                    tile.getStrategicValue(),
                    alpha,
                    beta,
                    player,
                    opponent,
                    false
            ));
        }

        int bestValue = -9999;
        for (Map.Entry<Tile, Integer> entry : tiles.entrySet()) {
            System.out.println("Best value " + bestValue);
            if(entry.getValue() > bestValue) {
                System.out.println("Better Move Found!");
                if(bestTile != null) {
                    System.out.println("Old Move value: " + bestTile.getRow() + " " + bestTile.getCol() + " score " + bestTile.getStrategicValue());
                }
                System.out.println("New Move Value " + entry.getKey().getRow() + entry.getKey().getCol() + " score " + entry.getKey().getStrategicValue());
                bestTile = entry.getKey();
                bestValue = entry.getValue();
            } else {
                System.out.println("IgnoreMove " + entry.getKey().getRow() + entry.getKey().getCol() + " score " + entry.getKey().getStrategicValue());
            }
        }


        if(bestTile != null) {
            System.out.println("RETURNS " + bestTile.getStrategicValue() + " BESTVAL " + bestValue);
        }
        return bestTile;
    }


    public int minimax2(int depth, Board board, int score, int alpha, int beta,
                        Player player, Player opponent, boolean maximizingPlayer) {

        if (depth == 0) {
            return score;
        }

        if (maximizingPlayer) {
            int maxScore = -999;
            List<Tile> moves = board.getAvailableMoves(player);

            if (moves.size() == 0) {
                return score;
            }

            for (Tile move : moves) {
                Board newBoard = new ReversiBoard(board.boardSize);
                newBoard.copyState(board);
                newBoard.setMove(move.getRow(), move.getCol(), player);

                int currentScore = minimax2(
                        depth - 1,
                        newBoard,
                        score + move.getStrategicValue(),
                        alpha, beta,
                        player,
                        opponent,
                        false
                );

                maxScore = max(maxScore, currentScore);

                alpha = max(alpha, currentScore);
                if (beta <= alpha) {
                    break;
                }
            }
            return maxScore;

        } else {
            int minScore = 999;
            List<Tile> moves = board.getAvailableMoves(opponent);

            if (moves.size() == 0) {
                return score;
            }

            for (Tile move : moves) {
                Board newBoard = new ReversiBoard(board.boardSize);
                newBoard.copyState(board);
                newBoard.setMove(move.getRow(), move.getCol(), opponent);

                int currentScore = minimax2(
                        depth - 1,
                        newBoard,
                        score - move.getStrategicValue(),
                        alpha,
                        beta,
                        player,
                        opponent,
                        true
                );

                minScore = min(minScore, currentScore);

                beta = min(beta, currentScore);
                if (beta <= alpha) {
                    break;
                }
            }
            return minScore;
        }
    }
}
