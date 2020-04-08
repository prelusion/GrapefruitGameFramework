package com.grapefruit.gamework.games.tictactoe;

import com.grapefruit.gamework.framework.Board;
import com.grapefruit.gamework.framework.Game;
import com.grapefruit.gamework.framework.GameFactory;
import com.grapefruit.gamework.framework.Player;
import com.grapefruit.gamework.games.reversi.Reversi;
import com.grapefruit.gamework.games.reversi.ReversiBoard;

public class TicTacToeFactory extends GameFactory {
    private static final int TICTACTOE_BOARDSIZE = 3;
    public TicTacToe create(Player[] players) {
        TicTacToeBoard board = new TicTacToeBoard(TICTACTOE_BOARDSIZE);
        int turnTimeout = 10;
        return new TicTacToe(board, players, turnTimeout);
    }
}
