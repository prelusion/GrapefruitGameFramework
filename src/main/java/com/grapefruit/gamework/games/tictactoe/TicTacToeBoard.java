package com.grapefruit.gamework.games.tictactoe;

import com.grapefruit.gamework.framework.Board;
import com.grapefruit.gamework.framework.Helpers;
import com.grapefruit.gamework.framework.Player;
import com.grapefruit.gamework.framework.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The type Tic tac toe board.
 */
public class TicTacToeBoard extends Board {
    /**
     * Constructor for making a new Board object.
     *
     * @param boardSize the board size
     */
    public TicTacToeBoard(int boardSize) {
        super(boardSize);
    }


    @Override
    public void setMove(int row, int col, Player player) {
        setPlayer(row, col, player);
    }

    @Override
    public List<Tile> getAvailableMoves(Player player) {
        ArrayList<Tile> tiles = new ArrayList<>();
        for (Tile[] row : grid) {
            for (Tile tile : row) {
                if (tile.getPlayer() == null) {
                    tiles.add(tile);
                }
            }
        }
        return tiles;
    }

    @Override
    public Map<Player, Integer> countPieces() {
        return null;
    }


    @Override
    public void calculateScores(Player[] players) {
        for (Player player : players) {
            scores.put(player, Helpers.countPiecesForPlayer(this, player));
        }
    }
}
