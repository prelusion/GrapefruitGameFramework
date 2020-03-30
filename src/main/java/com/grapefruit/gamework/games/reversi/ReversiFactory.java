package com.grapefruit.gamework.games.reversi;

import com.grapefruit.gamework.framework.Board;
import com.grapefruit.gamework.framework.GameFactory;
import com.grapefruit.gamework.framework.Player;

public class ReversiFactory extends GameFactory {

    public Reversi create(Player[] players) {
        Board board = new Board(8);
        int turnTimeout = 10;
        return new Reversi(board, players, turnTimeout);
    }
}
