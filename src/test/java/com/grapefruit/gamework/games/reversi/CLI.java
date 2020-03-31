package com.grapefruit.gamework.games.reversi;

import com.grapefruit.gamework.framework.Board;
import com.grapefruit.gamework.framework.Game;
import com.grapefruit.gamework.framework.Player;
import com.grapefruit.gamework.games.reversi.Reversi;

public class CLI {
    public static void main(String[] args) {
        int turnTimeout = 10;
        Board board = new ReversiBoard(9);
        Player playerWhite = new Player("White", "white");
        Player playerBlack = new Player("Black", "black");
        Game game = new Reversi(board, playerWhite, playerBlack, turnTimeout);

        int i = 0;

        int[][] steps = new int[][] {
                new int[] {0, 1},
        };

        board.printBoard();
    }
}
