
package com.grapefruit.gamework.games.reversi;

import com.grapefruit.gamework.framework.*;
import com.grapefruit.gamework.framework.Player;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.grapefruit.gamework.games.reversi.ReversiFactory.STRATEGIC_VALUES;

public class RealAI {
    /* AI vs AI */
    public static void main(String[] args) {
        int turnTimeout = 19;
        Board board = new ReversiBoard(8, STRATEGIC_VALUES);
        int depth = 12;

        Player playerWhite = new Player("White", Colors.WHITE, true);
        Player playerBlack = new Player("Black", Colors.BLACK, true);
        Reversi game = new Reversi(board, playerWhite, playerBlack, turnTimeout);
        MinimaxAlgorithm minimax = new MinimaxAlgorithm();

        Player currentPlayer = game.getCurrentPlayer();
        Player opponent = currentPlayer == playerWhite ? playerBlack : playerWhite;

        //CALCULATE Benchmark Depth;
//        ReversiBenchMark BM = new ReversiBenchMark(turnTimeout, minimax, currentPlayer, opponent);
//        int depth = BM.calculateDepth(1);
//        System.out.println("Depth " + depth);

        System.out.println("strategic values:");
        board.printStrategicValues();

        int turnCount = 1;
        while (!game.hasFinished()) {
            System.out.println("board state:");
            board.printBoard();

//            System.out.println(String.format("\n\n----------------turn %s--------------------", turnCount));

            currentPlayer = game.getCurrentPlayer();
//            System.out.println("current player: " + currentPlayer.getColor());

            List<Tile> availableMoves = game.getAvailableMoves(currentPlayer);
//            System.out.println("available moves: " + availableMoves.size());
//            board.printAvailableMovesWithStrategicValues(availableMoves);

            if (availableMoves.size() == 0) {
//                System.out.println("no moves available");

                if (game.hasFinished()) {
                    break;
                } else {
//                    System.out.println(game.getCurrentPlayer());
                    game.nextPlayer();
//                    System.out.println(game.getCurrentPlayer());
                    continue;
                }
            }

            opponent = currentPlayer == playerWhite ? playerBlack : playerWhite;
            Tile move;
            long startTime = System.currentTimeMillis()/1000;
            if (currentPlayer.getColor() == Colors.WHITE) {
                System.out.println(" Turn " + turnCount + " Depth " + depth);
                move = minimax.calculateBestMove(game.getBoard(), currentPlayer, opponent, depth);
                long endTime = System.currentTimeMillis()/1000;
                System.out.println("Time " + (endTime-startTime) + "\n");
                if(turnCount > 40) {
                    depth++;
                } else if( turnCount < 10 && depth > 8) {
                    depth--;
                } else if((endTime-startTime) <= 2) {
                    //depth++;
                } else if((endTime-startTime) >= 8) {
                    //depth--;
                }
            } else {
                move = minimax.calculateBestMove(game.getBoard(), currentPlayer, opponent, 5);
            }



////            System.out.println(String.format("play move: %s %s", move.getRow(), move.getCol()));
            game.playMove(move.getRow(), move.getCol(), currentPlayer);
            game.nextPlayer();
            turnCount++;
////            System.out.println("-----------------------------------");
        }

        System.out.println("-----------------------------------");
        System.out.println("game finished");

        System.out.println(String.format("white pieces: %s", board.countPieces(playerWhite)));
        System.out.println(String.format("black pieces: %s", board.countPieces(playerBlack)));

         if (game.hasWinner()) {
            System.out.println("winner: " + game.getWinner().getColor().toString());
        } else {
//            System.out.println("tie");
        }

//        System.out.println("strategic values:");
//        board.printStrategicValues();
    }
}
