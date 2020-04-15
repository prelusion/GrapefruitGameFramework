package com.grapefruit.gamework.games.tictactoe;

import com.grapefruit.gamework.framework.*;
import com.grapefruit.gamework.games.reversi.AI.OldMinimax;

/**
 * The type Tic tac toe.
 */
public class TicTacToe extends Game {

    /**
     * Instantiates a new Tic tac toe.
     *
     * @param board       the board
     * @param players     the players
     * @param turnTimeout the turn timeout
     */
    public TicTacToe(Board board, Player[] players, int turnTimeout) {
        super(board, players, new OldMinimax(board.getStrategicValues()), turnTimeout);
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
    public Player getWinner() {
        Tile tile = Conditions.getTileOfAvailableConditions(getBoard());
        if (tile != null) {
            return tile.getPlayer();
        }
        return null;
    }

    @Override
    public boolean playMove(int row, int col, Player player) {
        if (!isValidMove(row, col, player)) {
            return false;
        }

        getBoard().setMove(row, col, player);
        return true;
    }
}
