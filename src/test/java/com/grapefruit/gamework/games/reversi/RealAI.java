
package com.grapefruit.gamework.games.reversi;

import com.grapefruit.gamework.framework.*;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

import com.grapefruit.gamework.framework.player.AIPlayer;
import com.grapefruit.gamework.framework.player.Player;

public class RealAI {
    /* AI vs AI */
    public static void main(String[] args) throws InterruptedException {
        int turnTimeout = 10;
        Scanner in = new Scanner(System.in);
        Board board = new ReversiBoard(8);
        Player playerWhite = new AIPlayer("White", Colors.WHITE, new MinimaxAlgorithm(), 1);
        Player playerBlack = new AIPlayer("Black", Colors.BLACK, new MinimaxAlgorithm(), 1);
        Game game = new Reversi(board, playerWhite, playerBlack, turnTimeout);

        board.printBoard();

        while (!game.hasFinished()) {
            Thread.sleep(500);
            System.out.println("--------------------------------------");
            Player currentPlayer = game.getCurrentPlayer();

            System.out.println("-- AVAILABLE MOVES for player " + currentPlayer.getColor());
            board.printAvailableMoves(game.getCurrentPlayer());

            System.out.println("--------------------------------------");
            List<Tile> availableMoves = game.getAvailableMoves(currentPlayer);
            System.out.println("available moves: " + availableMoves.size());

            System.out.println("--------------------------------------");
            Tile move = ((AIPlayer)game.getCurrentPlayer()).calculateMove(game.getBoard());
            game.playMove(move.getRow(), move.getCol());
            System.out.println("Row " + move.getRow() + " Col " + move.getCol() + " Player " + currentPlayer.getColor());
            System.out.println(game.getBoard().getGrid()[move.getRow()][move.getCol()].getPlayer());

            System.out.println();

            if (availableMoves.size() > 0) {

//                System.out.println("row: " + move.getRow() + ", col: " + move.getCol());
//                boolean success = game.playMove(move.getRow(), move.getCol());
//                assert success;

                System.out.println("-- CURRENT BOARD -- ");
                board.printBoard();
            } else {
                System.out.println("testsertestestestst");
                //game.nextPlayer();
            }
        }

        Game.GameResult result = game.getGameResult();
        System.out.println("Result: " + result);
        if (game.hasWinner()) {
            System.out.println("Winner: " + game.getWinner().getColor().toString());
        }


    }
}

