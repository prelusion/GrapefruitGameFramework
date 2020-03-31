package com.grapefruit.gamework.games.tictactoe;

import com.grapefruit.gamework.framework.Board;
import com.grapefruit.gamework.framework.Player;

public class TicTacToeFactory {
    public static TicTacToe create(Player[] players) {
        Board board = new TicTacToeBoard(9);
        int turnTimeout = 10;
        return new TicTacToe(board, players, turnTimeout);
    }
}
