
package com.grapefruit.gamework.games.reversi;

import com.grapefruit.gamework.framework.*;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

import com.grapefruit.gamework.framework.player.AIPlayer;
import com.grapefruit.gamework.framework.player.Player;

public class RealAI {
    /* AI vs AI */
    public static void main(String[] args) {
        int turnTimeout = 10;
        Board board = new ReversiBoard(8);
        AIPlayer playerWhite = new AIPlayer("White", Colors.WHITE, new MinimaxAlgorithm(), 1);
        AIPlayer playerBlack = new AIPlayer("Black", Colors.BLACK, new MinimaxAlgorithm(), 3);
        Reversi game = new Reversi(board, playerWhite, playerBlack, turnTimeout);

        System.out.println("strategic values:");
        board.printStrategicValues();

        int turnCount = 1;
        while (!game.hasFinished()) {
            System.out.println("board state:");
            board.printBoard();

            System.out.println(String.format("\n\n----------------turn %s--------------------", turnCount));

            Player currentPlayer = game.getCurrentPlayer();
            System.out.println("current player: " + currentPlayer.getColor());

            List<Tile> availableMoves = game.getAvailableMoves(currentPlayer);
            System.out.println("available moves: " + availableMoves.size());
            board.printAvailableMoves(currentPlayer);

            if (availableMoves.size() == 0) {
                System.out.println("no moves available");
                continue;
            }

            Player opponent = currentPlayer == playerWhite ? playerBlack : playerWhite;

            Tile move = ((AIPlayer) currentPlayer).calculateMove(game.getBoard(), opponent);

            System.out.println(String.format("play move: %s %s", move.getRow(), move.getCol()));
            game.playMove(move.getRow(), move.getCol(), currentPlayer);

            turnCount++;
            System.out.println("-----------------------------------");
        }

        System.out.println("-----------------------------------");
        System.out.println("game finished");

        Game.GameResult result = game.getGameResult();
        System.out.println("result: " + result);

        System.out.println(String.format("white pieces: %s", board.countPieces(playerWhite)));
        System.out.println(String.format("black pieces: %s", board.countPieces(playerBlack)));

        if (game.hasWinner()) {
            System.out.println("Winner: " + game.getWinner().getColor().toString());
        }

        System.out.println("strategic values:");
        board.printStrategicValues();
    }
}
