package com.grapefruit.gamework.games.reversi;

import com.grapefruit.gamework.framework.Board;
import com.grapefruit.gamework.framework.Game;
import com.grapefruit.gamework.framework.Player;
import com.grapefruit.gamework.framework.Tile;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class ReversiTest {
    @Test
    public void printBoard() {
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
        while (i < steps.length) {
            System.out.println("--------------------------------");
            Player currentPlayer = game.getNextPlayer();

            board.printAvailableMoves(currentPlayer);

            int row = steps[i][0];
            int col = steps[i][1];

            boolean success = game.setMove(row, col, currentPlayer);
            assertTrue("Invalid move", success);

            i++;
            board.printBoard();
        }
    }
}
