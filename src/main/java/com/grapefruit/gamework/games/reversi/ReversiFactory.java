package com.grapefruit.gamework.games.reversi;

import com.grapefruit.gamework.framework.Board;
import com.grapefruit.gamework.framework.Game;
import com.grapefruit.gamework.framework.GameFactory;
import com.grapefruit.gamework.framework.player.Player;

public class ReversiFactory extends GameFactory {
    static final int DEFAULT_REVERSI_BOARD_SIZE = 8;

    public Reversi create(Player playerWhite, Player playerBlack) {
        Board board = new ReversiBoard(DEFAULT_REVERSI_BOARD_SIZE);
        int turnTimeout = 10;
        return new Reversi(board, playerWhite, playerBlack, turnTimeout);
    }

    @Override
    public Game create(Player[] players) {
        Board board = new ReversiBoard(DEFAULT_REVERSI_BOARD_SIZE);
        int turnTimeout = 10;
        return new Reversi(board, players[0], players[1], turnTimeout);
    }
}
