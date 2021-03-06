package com.grapefruit.gamework.games.reversi;

import com.grapefruit.gamework.framework.*;

import java.util.List;
import java.util.Random;
import java.util.Scanner;
import com.grapefruit.gamework.framework.Player;
import com.grapefruit.gamework.games.reversi.AI.NewMoveAlgorithm;

public class RandomAI {
    /* AI vs AI */
    public static void main(String[] args) {
        int turnTimeout = 10;
        Scanner in = new Scanner(System.in);
        Board board = new ReversiBoard(9, ReversiFactory.STRATEGIC_VALUES);
        Player playerWhite = new Player("White", Colors.WHITE, true);
        Player playerBlack = new Player("Black", Colors.BLACK, true);
        Game game = new Reversi(board, playerWhite, playerBlack, turnTimeout, new NewMoveAlgorithm(ReversiFactory.STRATEGIC_VALUES));

        board.printBoard();

        Random random = new Random();

        while (!game.hasFinished()) {
            System.out.println("--------------------------------------");
            Player currentPlayer = game.getCurrentPlayer();

            System.out.println("-- AVAILABLE MOVES for player " + currentPlayer.getColor());
            board.printAvailableMoves(game.getCurrentPlayer());
            System.out.println("--------------------------------------");

            List<Tile> availableMoves = game.getAvailableMoves(currentPlayer);
            System.out.println("available moves: " + availableMoves.size());

            if (availableMoves.size() > 0) {
                int idx = random.nextInt(availableMoves.size());
                System.out.println("random move index: " + idx);
                Tile move = availableMoves.get(idx);

                System.out.println("row: " + move.getRow() + ", col: " + move.getCol());
                boolean success = game.playMove(move.getRow(), move.getCol(), currentPlayer);
                assert success;

                System.out.println("-- CURRENT BOARD -- ");
                board.printBoard();


            } else {
                game.nextPlayer();
            }
        }

        if (game.hasWinner()) {
            System.out.println("Winner: " + game.getWinner().getColor().toString());
        }


    }
}
