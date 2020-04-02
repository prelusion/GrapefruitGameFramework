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

        List<Tile> moves = board.getAvailableMoves(player);
        for (Tile tile : moves) {
            Board newBoard = new ReversiBoard(board.boardSize);
            newBoard.copyState(board);
            newBoard.setMove(tile.getRow(), tile.getCol(), player);
            System.out.println("Maximizing first depth");
            newBoard.printBoard();
            int score = minimax2(
                    depth - 1,
                    newBoard,
                    tile.getStrategicValue(),
                    alpha,
                    beta,
                    player,
                    opponentPlayer,
                    false
            );

            if (score > maxScore) {
                maxScore = score;
                bestTile = tile;
            }
        }

        if (bestTile == null) return null;

        if (bestTile.getStrategicValue() == -24) {
            System.out.println("WRONG MOVE!");
            moves.forEach(move ->
                    System.out.println(move.getStrategicValue() + "," + move.getRow()  + "," +  move.getCol()));
        }

        return bestTile;
    }


    public int minimax2(int depth, Board board, int score, int alpha, int beta,
                        Player player, Player opponentPlayer, boolean maximizingPlayer) {

        List<Tile> moves = board.getAvailableMoves(player);

        if (depth == 0 || moves.size() == 0) {
            return score;
        }

        if (maximizingPlayer) {
            int maxScore = -999;
            for (Tile move : moves) {
                System.out.println("MAXIMIZING");

                Board newBoard = new ReversiBoard(board.boardSize);
                newBoard.copyState(board);
                newBoard.printAvailableMoves(player);
                newBoard.setMove(move.getRow(), move.getCol(), player);
                newBoard.printBoard();
                int currentScore = minimax2(
                        depth - 1,
                        newBoard,
                        move.getStrategicValue(),
                        alpha, beta,
                        player,
                        opponentPlayer,
                        false
                );
                System.out.println("maximizing current score: " + currentScore);

                maxScore = max(maxScore, currentScore);

//                alpha = max(alpha, currentScore);
//                if (beta <= alpha) {
//                    break;
//                }
            }
            System.out.println("maximizing depth:" + depth);
            System.out.println("-----");
            return maxScore;

        } else {
            int minScore = 999;
            for (Tile move : moves) {
                System.out.println("MINIMIZING");

                Board newBoard = new ReversiBoard(board.boardSize);
                newBoard.copyState(board);
                newBoard.printAvailableMoves(opponentPlayer);
                newBoard.setMove(move.getRow(), move.getCol(), opponentPlayer);
                System.out.println("minimizing set move + " + move.getRow() + ", " + move.getCol());
                newBoard.printBoard();

                int currentScore = minimax2(
                        depth - 1,
                        newBoard,
                        move.getStrategicValue(),
                        alpha,
                        beta,
                        player,
                        opponentPlayer,
                        true
                );
                System.out.println("minimizing current score: " + currentScore);

                minScore = min(minScore, currentScore);

//                beta = min(beta, currentScore);
//                if (beta <= alpha) {
//                    break;
//                }
            }
            System.out.println("minimizing depth:" + depth);
            System.out.println("-----");
            return minScore;
        }
    }
}
