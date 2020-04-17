package com.grapefruit.gamework.games.tictactoe;

import com.grapefruit.gamework.framework.GameFactory;
import com.grapefruit.gamework.framework.Player;

/**
 * The type Tic tac toe factory.
 */
public class TicTacToeFactory extends GameFactory {
    /**
     * The constant TICTACTOE_BOARDSIZE
     */
    private static final int TICTACTOE_BOARDSIZE = 3;

    /**
     * Instantiates a new board and create a game object.
     *
     * @param players
     * @return game
     */
    public TicTacToe create(Player[] players) {
        TicTacToeBoard board = new TicTacToeBoard(TICTACTOE_BOARDSIZE);
        int turnTimeout = 10;
        return new TicTacToe(board, players, turnTimeout);
    }

    /**
     * Instantiates a new board and create a game object.
     *
     * @param players
     * @param difficulty
     * @return game
     */
    public TicTacToe create(Player[] players, int difficulty) {
        return create(players);
    }
}
