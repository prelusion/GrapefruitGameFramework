package com.grapefruit.gamework.framework;

import com.grapefruit.gamework.framework.player.AIPlayer;
import com.grapefruit.gamework.framework.player.Player;
import com.grapefruit.gamework.games.tictactoe.TicTacToe;
import com.grapefruit.gamework.games.tictactoe.TicTacToeBoard;
import org.junit.Test;

public class BoardTest {
//    @Test
//    public void printBoard() {
//        Player[] players = new Player[2];
//        players[0] = new AIPlayer("Delano", Colors.BLACK, new MinimaxAlgorithm(), 19);
//        players[1] = new AIPlayer("Lorenzo", Colors.WHITE, new MinimaxAlgorithm(), 19);
//        TicTacToe game = new TicTacToe(new TicTacToeBoard(3), players, 10);
//        Tile tile;
//
//        game.getBoard().printBoard();
//        for (int i = 0; i < game.getBoard().getGrid().length * game.getBoard().getGrid().length; i++) {
//            System.out.println("Turn " + i + " " + game.getCurrentPlayer().getColor().toString());
//            tile = ((AIPlayer)game.getCurrentPlayer()).calculateMove(game.getBoard(),);
//            game.playMove(tile.getRow(), tile.getCol());
//            game.getBoard().printBoard();
//            if(game.finished) {
//                showResult(game);
//                System.exit(0);
//            }
//        }
//    }
//
//    public void showResult(Game game) {
//        game.getGameResult();
//        System.out.println("GameFinished Player: " + game.getWinner() + " Won!");
//    }
}