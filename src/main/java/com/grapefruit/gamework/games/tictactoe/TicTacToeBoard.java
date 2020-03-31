package com.grapefruit.gamework.games.tictactoe;

import com.grapefruit.gamework.framework.Board;
import com.grapefruit.gamework.framework.player.Player;
import com.grapefruit.gamework.framework.Tile;

import java.util.HashSet;

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
    public HashSet<Tile> getAvailableMoves(Player player) {
        HashSet<Tile> tiles = new HashSet<>();
        for (Tile[] row: this.getGrid()){
            for (Tile tile: row) {
                if(tile.getPlayer() == null) {
                    tiles.add(tile);
                }
            }
        }
        return tiles;
    }
}
