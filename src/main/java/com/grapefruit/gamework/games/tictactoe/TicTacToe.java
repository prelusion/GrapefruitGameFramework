package com.grapefruit.gamework.games.tictactoe;

import com.grapefruit.gamework.framework.*;
import com.grapefruit.gamework.framework.player.Player;

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
    public boolean hasFinished() {
        return getBoard().isBoardFull() || Conditions.checkAllAdjacentConditions(getBoard());
    }

    @Override
    public GameResult getGameResult() {
        Tile tile = Conditions.getTileOfAvailableConditions(getBoard());
        if (tile != null) {
            return GameResult.WINNER;
        } else {
            if (finished){
                return GameResult.TIE;
            } else {
                return GameResult.NONE;
            }
        }
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
        return getBoard().getAvailableMoves(player);
    }

    @Override
    public boolean doMove(int row, int col, Player player) {
        if (!isValidMove(row, col, player)) {
            return false;
        }

        getBoard().setPlayer(row, col, getCurrentPlayer());
        if (hasFinished()) {
            finished = true;
        }
        return true;
    }

    @Override
    public boolean playMove(int row, int col, Player player){
        if(doMove(row, col, player)) {
            nextPlayer();
            return true;
        }
        return false;
    }
}
