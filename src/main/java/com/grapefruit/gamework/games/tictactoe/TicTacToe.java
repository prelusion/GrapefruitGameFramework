package com.grapefruit.gamework.games.tictactoe;

import com.grapefruit.gamework.framework.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class TicTacToe extends Game {

    /**
     * Constructor of making an GameSession
     *
     * @param board
     * @param players
     * @param turnTimeout
     */
    public TicTacToe(Board board, Player[] players, int turnTimeout) {
        super(board, players, turnTimeout);
    }

    @Override
    public boolean isValidMove(int row, int col, Player player) {
        return !getBoard().hasPlayer(row, col);
    }

    @Override
    public boolean hasGameFinished() {
        return getBoard().isBoardFull() || Conditions.checkAllAdjacentConditions(getBoard());
    }

    @Override
    public void calculateGameResult() {
        if (!finished)
            gameResult = GameResult.NONE;
        if (Conditions.checkAllAdjacentConditions(getBoard()))
            gameResult = GameResult.WINNER;
        else
            gameResult = GameResult.TIE;
    }


    @Override
    public Player getWinner() {
        if (getGameResult() == GameResult.NONE) {
            return null;
        }

        Tile tile = Conditions.getTileOfAvailableConditions(getBoard());
        if (tile != null) {
            System.out.println("row " + tile.getRow() + " col " + tile.getCol());
            return tile.getPlayer();
        }

        return null;
    }

    @Override
    public List<Tile> getAvailableMoves(Player player) {
        ArrayList<Tile> tiles = new ArrayList<>();
        for (Tile[] column: getBoard().getGrid()){
            for (Tile tile: column) {
                if (!tiles.contains(tile)) tiles.add(tile);
            }
        }
        return tiles;
    }
}
