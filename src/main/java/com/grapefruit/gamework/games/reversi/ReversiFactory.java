package com.grapefruit.gamework.games.reversi;

import com.grapefruit.gamework.framework.Board;
import com.grapefruit.gamework.framework.Game;
import com.grapefruit.gamework.framework.GameFactory;
import com.grapefruit.gamework.framework.Player;

public class ReversiFactory extends GameFactory {
    public Reversi create(Player playerWhite, Player playerBlack) {
        Board board = new ReversiBoard(9);
        int turnTimeout = 10;
        return new Reversi(board, playerWhite, playerBlack, turnTimeout);
    }

    @Override
    public Game create(Player[] players) {
        Board board = new ReversiBoard(9);
        int turnTimeout = 10;
        return new Reversi(board, players[0], players[1], turnTimeout);
    }
}
