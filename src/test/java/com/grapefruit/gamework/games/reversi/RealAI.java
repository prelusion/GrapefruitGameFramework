
package com.grapefruit.gamework.games.reversi;

import com.grapefruit.gamework.framework.*;
import com.grapefruit.gamework.framework.Player;

import java.util.List;

import static com.grapefruit.gamework.games.reversi.ReversiFactory.STRATEGIC_VALUES;

public class RealAI {
    /* AI vs AI */
    public static void main(String[] args) {
        int turnTimeout = 10;
        Board board = new ReversiBoard(8, STRATEGIC_VALUES);
        int playerWhiteDepth = 1;
        int playerBlackDepth = 5;


        Player playerWhite = new Player("White", Colors.WHITE, true);
        Player playerBlack = new Player("Black", Colors.BLACK, true);
        Reversi game = new Reversi(board, playerWhite, playerBlack, turnTimeout);
        MinimaxAlgorithm minimax = new MinimaxAlgorithm(game);

        System.out.println("strategic values:");
        board.printStrategicValues();

        int turnCount = 1;
        while (!game.hasFinished()) {
            System.out.println("board state:");
            board.printBoard();

            System.out.println(String.format("\n\n----------------turn %s--------------------", turnCount));

            Player currentPlayer = game.getCurrentPlayer();
            System.out.println("current player: " + currentPlayer.getColor());
            System.out.println("minimax depth: " + (currentPlayer == playerWhite ? playerWhiteDepth : playerBlackDepth));

            List<Tile> availableMoves = game.getAvailableMoves(currentPlayer);
            System.out.println("available moves: " + availableMoves.size());
            board.printAvailableMovesWithStrategicValues(availableMoves);

            if (availableMoves.size() == 0) {
                System.out.println("no moves available");

                if (game.hasFinished()) {
                    break;
                } else {
                    System.out.println(game.getCurrentPlayer());
                    game.nextPlayer();
                    System.out.println(game.getCurrentPlayer());
                    continue;
                }
            }

            Player opponent = currentPlayer == playerWhite ? playerBlack : playerWhite;
            Tile move;
            if (currentPlayer.getColor() == Colors.BLACK) {
                move = minimax.calculateBestMove(game.getBoard(), currentPlayer, opponent, 7);
            } else {
                move = minimax.calculateBestMove(game.getBoard(), currentPlayer, opponent, 2);
            }

            System.out.println(String.format("play move: %s %s", move.getRow(), move.getCol()));
            game.playMove(move.getRow(), move.getCol(), currentPlayer);
            game.nextPlayer();
            turnCount++;
            System.out.println("-----------------------------------");
        }

        System.out.println("-----------------------------------");
        System.out.println("game finished");

        System.out.println(String.format("white pieces: %s", board.countPieces(playerWhite)));
        System.out.println(String.format("black pieces: %s", board.countPieces(playerBlack)));

        if (game.hasWinner()) {
            System.out.println("winner: " + game.getWinner().getColor().toString());
        } else {
            System.out.println("tie");
        }

        System.out.println("strategic values:");
        board.printStrategicValues();
    }
}