package com.grapefruit.gamework.games.reversi;

import com.grapefruit.gamework.framework.*;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class CLI {

    /* Manual */
//    public static void main(String[] args) {
//        int turnTimeout = 10;
//        Scanner in = new Scanner(System.in);
//        Board board = new ReversiBoard(9);
//        Player playerWhite = new Player("White", Colors.WHITE);
//        Player playerBlack = new Player("Black", Colors.BLACK);
//        Game game = new Reversi(board, playerWhite, playerBlack, turnTimeout);
//
//        board.printBoard();
//
//        while (true) {
//            System.out.println("--------------------------------------");
//
//            game.nextPlayer();
//            Player currentPlayer = game.getCurrentPlayer();
//
//            System.out.println("available moves for player " + currentPlayer.getColor());
//            board.printAvailableMoves(game.getCurrentPlayer());
//            System.out.println("--------------------------------------");
//
//            boolean success = false;
//            while (!success) {
//                String line = in.nextLine();
//                if (line.charAt(0) == 'q') return;
//
//                int row = Character.getNumericValue(line.charAt(0));
//                int col = Character.getNumericValue(line.charAt(1));
//                System.out.println("row: " + row + ", col: " + col);
//
//                success = game.setMove(row, col);
//                if (!success) System.out.println("invalid move");
//            }
//
//            board.printBoard();
//        }
//    }

    /* AI vs AI */
    public static void main(String[] args) {
        int turnTimeout = 10;
        Scanner in = new Scanner(System.in);
        Board board = new ReversiBoard(9);
        Player playerWhite = new Player("White", Colors.WHITE);
        Player playerBlack = new Player("Black", Colors.BLACK);
        Game game = new Reversi(board, playerWhite, playerBlack, turnTimeout);

        board.printBoard();

        Random random = new Random();

        while (true) {
            System.out.println("--------------------------------------");

            game.nextPlayer();
            Player currentPlayer = game.getCurrentPlayer();

            System.out.println("available moves for player " + currentPlayer.getColor());
            board.printAvailableMoves(game.getCurrentPlayer());
            System.out.println("--------------------------------------");


            List<Tile> availableMoves = game.getAvailableMoves(currentPlayer);
            System.out.println("available moves: " + availableMoves.size());

            if (availableMoves.size() < 1) break;

            int idx = random.nextInt(availableMoves.size());
            System.out.println("random move index: " + idx);
            Tile move = availableMoves.get(idx);

            System.out.println("row: " + move.getRow() + ", col: " + move.getCol());
            boolean success = game.setMove(move.getRow(), move.getCol());
            assert success;

            board.printBoard();
        }
    }
}
