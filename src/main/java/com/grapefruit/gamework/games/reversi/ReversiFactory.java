package com.grapefruit.gamework.games.reversi;

import com.grapefruit.gamework.framework.Board;
import com.grapefruit.gamework.framework.Player;

public class ReversiFactory {
    public static Reversi create(Player playerWhite, Player playerBlack) {
        Board board = new ReversiBoard(9);
        int turnTimeout = 10;
        return new Reversi(board, playerWhite, playerBlack, turnTimeout);
    }
}
