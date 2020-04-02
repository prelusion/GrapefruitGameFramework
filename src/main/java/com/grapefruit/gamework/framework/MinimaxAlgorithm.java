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
    public int minimax(int depth, Board board, Tile tile, int alpha, int beta, Player player, Player opponentPlayer, boolean maximizingPlayer) {
        return 0;
    }

    @Override
    public Tile calculateBestMove(Board board, Player player, int depth) {
        Player opponentPlayer;
        if (player.getColor() == Colors.BLACK) {
            opponentPlayer = new AIPlayer("Opponent", Colors.WHITE, new MinimaxAlgorithm(), depth);
        } else {
            opponentPlayer = new AIPlayer("Opponent", Colors.BLACK, new MinimaxAlgorithm(), depth);
        }

        int alpha = -999999;
        int beta = +999999;
        int maxScore = -9999999;
        Tile bestTile= null;

        for (Tile tile : board.getAvailableMoves(player)) {
            int score = minimax2(depth, board, maxScore, alpha, beta, player, opponentPlayer, true);
            if (score > maxScore) {
                maxScore = score;
                bestTile = tile;
            }
        }

        return bestTile;
    }

    public int minimax2(int depth, Board board, int score, int alpha, int beta, Player player, Player opponentPlayer, boolean maximizingPlayer) {
        List<Tile> moves = board.getAvailableMoves(player);

        if (depth == 0 || moves.size() == 0) {
            return score;
        }

        if (maximizingPlayer) {
            int maxScore = -999;
            for (Tile move : moves) {
                Board newBoard = new ReversiBoard(board.boardSize);
                newBoard.copyState(board);
                newBoard.setMove(move.getRow(), move.getCol(), player);
                score += move.getStrategicValue();

                int currentScore = minimax2(depth - 1, newBoard, score, alpha, beta, player, opponentPlayer, false);
                maxScore = max(maxScore, currentScore);

                alpha = max(alpha, currentScore);
                if (beta <= alpha) {
                    break;
                }
            }
            return maxScore;

        } else {
            int minScore = 999;
            for (Tile move : moves) {
                Board newBoard = new ReversiBoard(board.boardSize);
                newBoard.copyState(board);
                newBoard.setMove(move.getRow(), move.getCol(), opponentPlayer);
                score += move.getStrategicValue();

                int currentScore = minimax2(depth - 1, newBoard, score, alpha, beta, player, opponentPlayer, true);
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
