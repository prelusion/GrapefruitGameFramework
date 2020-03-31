package com.grapefruit.gamework.games.reversi;

import com.grapefruit.gamework.framework.Board;
import com.grapefruit.gamework.framework.Colors;
import com.grapefruit.gamework.framework.Game;
import com.grapefruit.gamework.framework.Player;

import java.util.Scanner;

public class CLI {

    /* Manual */
    public static void main(String[] args) {
        int turnTimeout = 10;
        Scanner in = new Scanner(System.in);
        Board board = new ReversiBoard(9);
        Player playerWhite = new Player("White", Colors.WHITE);
        Player playerBlack = new Player("Black", Colors.BLACK);
        Game game = new Reversi(board, playerWhite, playerBlack, turnTimeout);

        board.printBoard();

        while (true) {
            System.out.println("--------------------------------------");

            game.nextPlayer();
            Player currentPlayer = game.getCurrentPlayer();

            System.out.println("available moves for player " + currentPlayer.getColor());
            board.printAvailableMoves(game.getCurrentPlayer());
            System.out.println("--------------------------------------");

            boolean success = false;
            while (!success) {
                String line = in.nextLine();
                if (line.charAt(0) == 'q') return;

                int row = Character.getNumericValue(line.charAt(0));
                int col = Character.getNumericValue(line.charAt(1));
                System.out.println("row: " + row + ", col: " + col);

                success = game.setMove(row, col);
                if (!success) System.out.println("invalid move");
            }

            board.printBoard();
        }
    }
}
