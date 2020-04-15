package com.grapefruit.gamework.games.tictactoe;

import com.grapefruit.gamework.framework.GameFactory;
import com.grapefruit.gamework.framework.Player;

/**
 * The type Tic tac toe factory.
 */
public class TicTacToeFactory extends GameFactory {
    private static final int TICTACTOE_BOARDSIZE = 3;


    public TicTacToe create(Player[] players) {
        TicTacToeBoard board = new TicTacToeBoard(TICTACTOE_BOARDSIZE);
        int turnTimeout = 10;
        return new TicTacToe(board, players, turnTimeout);
    }

    public TicTacToe create(Player[] players, int difficulty) {
        return create(players);
    }
}
