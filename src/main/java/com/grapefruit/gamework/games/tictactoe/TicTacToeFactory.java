package com.grapefruit.gamework.games.tictactoe;

import com.grapefruit.gamework.framework.Board;
import com.grapefruit.gamework.framework.GameFactory;
import com.grapefruit.gamework.framework.Player;

public class TicTacToeFactory extends GameFactory {
    public TicTacToe create(Player[] players) {
        Board board = new Board(3);
        int turnTimeout = 10;
        return new TicTacToe(board, players, turnTimeout);
    }
}
