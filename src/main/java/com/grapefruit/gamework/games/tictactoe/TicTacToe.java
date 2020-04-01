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
    public void calculateGameResult() {
        if (!finished) {
            gameResult = GameResult.NONE;
            
        } else if (Conditions.checkAllAdjacentConditions(getBoard()))
            gameResult = GameResult.WINNER;
        } else {
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
        return getBoard().getAvailableMoves(player);
    }

    @Override
    public boolean doMove(int row, int col) {
        if (!isValidMove(row, col, getCurrentPlayer())) {
            return false;
        }

        getBoard().setPlayer(row, col, getCurrentPlayer());
        if (hasGameFinished()) {
            finished = true;
            calculateGameResult();
        }
        return true;
    }

    @Override
    public boolean playMove(int row, int col){
        if(doMove(row, col)) {
            nextPlayer();
            return true;
        }
        return false;
    }
}
