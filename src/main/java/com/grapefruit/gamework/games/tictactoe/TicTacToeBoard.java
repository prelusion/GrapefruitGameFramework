package com.grapefruit.gamework.games.tictactoe;

import com.grapefruit.gamework.framework.Board;
import com.grapefruit.gamework.framework.Tile;
import com.grapefruit.gamework.framework.player.Player;

import java.util.ArrayList;
import java.util.List;

public class TicTacToeBoard extends Board {
    /**
     * Constructor for making a new Board object.
     *
     * @param boardSize
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
        for (Tile[] row : this.getGrid()) {
            for (Tile tile : row) {
                if (tile.getPlayer() == null) {
                    tiles.add(tile);
                }
            }
        }
        return tiles;
    }
}
