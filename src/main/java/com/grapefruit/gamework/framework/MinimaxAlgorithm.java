package com.grapefruit.gamework.framework;

import com.grapefruit.gamework.framework.player.AIPlayer;
import com.grapefruit.gamework.framework.player.Player;
import com.grapefruit.gamework.games.tictactoe.TicTacToeBoard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.max;
import static java.lang.Integer.min;

public class MinimaxAlgorithm implements MoveAlgorithm {
    public MinimaxAlgorithm() {  }
    @Override
    public Tile calculateBestMove(Board board, Player player, int depth) {
        Board AIBoard = new TicTacToeBoard(board.boardSize);
        AIBoard.copyState(AIBoard, board);
        int alpha = 999;
        int beta = -999;

        Player opponentPlayer;
        if(player.getColor() == Colors.BLACK) {
            opponentPlayer = new AIPlayer("Opponent", Colors.WHITE, new MinimaxAlgorithm(), depth);
        } else {
            opponentPlayer = new AIPlayer("Opponent", Colors.BLACK, new MinimaxAlgorithm(), depth);
        }

        List<Tile> tiles = AIBoard.getAvailableMoves(player);
        Map<Tile, Integer> tileScores = new HashMap<>();
        for(Tile tile: tiles) {
            AIBoard.setMove(tile.getRow(), tile.getCol(), player);
            tileScores.put(tile, getBestMoveValue(depth, AIBoard, tile, alpha, beta, opponentPlayer, player, true));
        }

        for (Map.Entry<Tile, Integer> entry: tileScores.entrySet()) {
            System.out.println("Row " + entry.getKey().getRow() + " Col " + entry.getKey().getCol());
            System.out.println("Score " + entry.getValue() + "\n");
        }

        /* Grabs the highest score Tile and returns it */
        return tileScores.entrySet().stream().max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1).get().getKey();
    }


    @Override
    public int getBestMoveValue(int depth, Board board, Tile position, int alpha, int beta, Player player, Player opponentPlayer, boolean maximizingPlayer) {
        List<Tile> tiles = board.getAvailableMoves(player);
        if(depth == 0 || tiles.isEmpty()) {
            int strategicValue = 0;
            if (Conditions.getTileOfAvailableConditions(board) != null) {
                Tile tile = Conditions.getTileOfAvailableConditions(board);
                if(tile.getPlayer() == opponentPlayer) {
                    strategicValue += 10;
                } else {
                    strategicValue -= 10;
                }
            }
            return strategicValue;
        }

        int strategicValue;
        if(maximizingPlayer) {
            int maxStrategicValue = -999;
            for(Tile newPosition: tiles) {
                board.setMove(newPosition.getRow(), newPosition.getCol(), player);
                strategicValue = getBestMoveValue(depth - 1, board, newPosition, alpha, beta, opponentPlayer, player, false);
                maxStrategicValue = max(maxStrategicValue, strategicValue);
                alpha = max(alpha, strategicValue);
                if(beta <= alpha) {
                    break;
                }
            }
            return maxStrategicValue;
        } else {
            int minStrategicValue = 999;
            for(Tile newPosition: tiles) {
                board.setMove(newPosition.getRow(), newPosition.getCol(), player);
                strategicValue = getBestMoveValue(depth - 1, board, newPosition, alpha, beta, opponentPlayer, player, true );

                minStrategicValue = min(minStrategicValue, strategicValue);
                beta = min(beta, strategicValue);
                if(beta <= alpha) {
                    break;
                }
            }
            return minStrategicValue;
        }
    }
}
