package com.grapefruit.gamework.games.tictactoe;

import com.grapefruit.gamework.framework.Board;
import com.grapefruit.gamework.framework.Player;
import com.grapefruit.gamework.framework.Tile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
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
        for (Tile[] row: this.getGrid()){
            tiles.addAll(Arrays.asList(row));
        }
        return tiles;
    }
}
