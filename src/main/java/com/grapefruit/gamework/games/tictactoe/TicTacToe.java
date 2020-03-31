package com.grapefruit.gamework.games.tictactoe;

import com.grapefruit.gamework.framework.*;

import java.util.HashSet;

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
    public HashSet<Tile> getAvailableMoves(Player player) {
        HashSet<Tile> tiles = new HashSet<>();
        for (Tile[] column: getBoard().getGrid()){
            for (Tile tile: column){
                tiles.add(tile);
            }
        }
        return tiles;
    }
}
