package com.grapefruit.gamework.games.tictactoe;

import com.grapefruit.gamework.framework.GameFactory;
import com.grapefruit.gamework.framework.Player;

public class TicTacToeFactory extends GameFactory {
    public TicTacToe create(Player[] players) {
        TicTacToeBoard board = new TicTacToeBoard(3);
        int turnTimeout = 10;
        return new TicTacToe(board, players, turnTimeout);
    }
}
