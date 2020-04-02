
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
        AIPlayer playerWhite = new AIPlayer("White", Colors.WHITE, new MinimaxAlgorithm(), 7);
        AIPlayer playerBlack = new AIPlayer("Black", Colors.BLACK, new MinimaxAlgorithm(), 1);
        Reversi game = new Reversi(board, playerWhite, playerBlack, turnTimeout);

        System.out.println("INIT BOARD:");
        board.printBoard();
        System.out.println("STRATEGIC VALUES");
        board.printStrategicValues();

        while (!game.hasFinished()) {
//            Thread.sleep(50);
            System.out.println("\n\n----------------NEW TURN--------------------");

            Player currentPlayer = game.getCurrentPlayer();
            System.out.println("current player: " + currentPlayer.getColor());
            List<Tile> availableMoves = game.getAvailableMoves(currentPlayer);
            System.out.println("available moves: " + availableMoves.size());
            board.printAvailableMoves(currentPlayer);

            Player opponent;
            if (currentPlayer == playerWhite) {
                opponent = playerBlack;
            } else {
                opponent = playerWhite;
            }
            Tile move = ((AIPlayer) currentPlayer).calculateMove(game.getBoard(), opponent);

            if (move == null) {
                System.out.println("WTF MOVE IS NULL");
                game.nextPlayer();
                game.checkFinished();
                continue;
            }

            game.playMove(move.getRow(), move.getCol(), currentPlayer);
            System.out.println("Row " + move.getRow() + " Col " + move.getCol() + " Player " + currentPlayer.getColor());
            System.out.println(game.getBoard().getGrid()[move.getRow()][move.getCol()].getPlayer());

            System.out.println();

            if (availableMoves.size() > 0) {
                System.out.println("current board");
                board.printBoard();
            }
//            System.exit(0);
        }

        Game.GameResult result = game.getGameResult();
        System.out.println("Result: " + result);
        if (game.hasWinner()) {
            System.out.println("Winner: " + game.getWinner().getColor().toString());
        }

        System.out.println("STRATEGIC VALUES");
        board.printStrategicValues();
    }
}

