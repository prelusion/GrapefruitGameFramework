package com.grapefruit.gamework.framework;

import com.grapefruit.gamework.framework.player.AIPlayer;
import com.grapefruit.gamework.framework.player.Player;
import com.grapefruit.gamework.games.reversi.ReversiBoard;
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
        Board AIBoard = new ReversiBoard(board.boardSize);
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
            tileScores.put(tile, getBestMoveValue(depth, AIBoard, tile, alpha, beta, player, true));
        }
        int bestValue = -99;
        Tile bestTile = null;
        for (Map.Entry<Tile, Integer> entry: tileScores.entrySet()) {
            System.out.println("Row " + entry.getKey().getRow() + " Col " + entry.getKey().getCol());
            System.out.println("Score " + entry.getValue() + "\n");

            if(entry.getKey().getStrategicValue() > bestValue) {
                bestValue = entry.getValue();
                bestTile = entry.getKey();
            }
        }
        System.out.println("Bestvalue " + bestValue + " BestTile row " + bestTile.getRow() + " col " + bestTile.getCol());




        /* Grabs the highest score Tile and returns it */
        return bestTile;
    }


    @Override
    public int getBestMoveValue(int depth, Board board, Tile position, int alpha, int beta, Player player, boolean maximizingPlayer) {
        List<Tile> tiles = board.getAvailableMoves(player);
        int maxStrategicValue = -999;
        int minStrategicValue = 999;
        if(depth == 0 || tiles.size() <= 0) {
            return position.getStrategicValue();
        }

        int strategicValue;
        if(maximizingPlayer) {
            for(Tile newPosition: tiles) {
                board.setMove(newPosition.getRow(), newPosition.getCol(), player);
                strategicValue = getBestMoveValue(depth - 1, board, newPosition, alpha, beta, player,false);

                maxStrategicValue = max(maxStrategicValue, strategicValue);
                alpha = max(alpha, strategicValue);
                if(beta <= alpha) {
                    break;
                }
            }
            return maxStrategicValue;
        } else {
            for(Tile newPosition: tiles) {
                board.setMove(newPosition.getRow(), newPosition.getCol(), player);
                strategicValue = getBestMoveValue(depth - 1, board, newPosition, alpha, beta, player,true );

                minStrategicValue = min(minStrategicValue, strategicValue);
                beta = min(beta, strategicValue);
                if(beta <= alpha) {
                    break;
                }
            }
            return minStrategicValue;
        }
    }


    //TICTACTOE

    //    if (Conditions.getTileOfAvailableConditions(board) != null) {
    //        Tile tile = Conditions.getTileOfAvailableConditions(board);
    //        if(tile.getPlayer() == opponentPlayer) {
    //            strategicValue += 10;
    //        } else {
    //            strategicValue -= 10;
    //        }
    //    }

}
