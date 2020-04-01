package com.grapefruit.gamework.games.reversi;

import com.grapefruit.gamework.framework.Board;
import com.grapefruit.gamework.framework.Game;
import com.grapefruit.gamework.framework.GameFactory;
import com.grapefruit.gamework.framework.player.Player;

public class ReversiFactory extends GameFactory {
    public static Reversi create(Player playerWhite, Player playerBlack) {
//        int[] strategicValues = new int[];

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

/*
Calculate strategic values Reversi

(row, col)

99
min, min
min, max
max, min
max, max

-8

min, max-1
min, min+1
max, min+1
max, min-1
min+1, min
max-1, max
max-1, min
max-1, max

8
min, max-2
min, min+2
max, min+2
max, min-2
min+2, min
max-2, max
max-2, min
max-2, max

6
min, min+3
min, max-3
max, min+3
max, min-3
min+3, min
max-3, max
max-3, min
max-3, max
 */
