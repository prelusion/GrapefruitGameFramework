package com.grapefruit.gamework.games.tictactoe;

import com.grapefruit.gamework.framework.*;

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
        return !getBoard().hasPiece(row, col);
    }

    @Override
    public boolean hasGameFinished() {
        return getBoard().isBoardFull();
    }

    @Override
    public boolean hasWinner() {
        if(!finished) {
            return false;
        }

        return Conditions.checkAllAdjacentConditions(getBoard());
    }

    @Override
    public boolean isTie() {
        if(!finished) {
            return false;
        }

        return !Conditions.checkAllAdjacentConditions(getBoard());
    }

    @Override
    public Player getWinner() {
        if (!finished || !hasWinner()) {
            return null;
        }
        Tile tile = Conditions.getTileOfAvailableConditions(getBoard());
        if(tile != null) {
            return tile.getPlayer();
        }
        return null;
    }

    @Override
    public List<Tile> getAvailableMoves(Player player) {
        return null;
    }
}
