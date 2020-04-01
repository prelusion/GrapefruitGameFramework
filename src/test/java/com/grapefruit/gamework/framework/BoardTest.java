package com.grapefruit.gamework.framework;

import com.grapefruit.gamework.framework.player.AIPlayer;
import com.grapefruit.gamework.framework.player.Player;
import com.grapefruit.gamework.games.tictactoe.TicTacToe;
import com.grapefruit.gamework.games.tictactoe.TicTacToeBoard;
import org.junit.Test;

public class BoardTest {
    @Test
    public void printBoard() {
        Player[] players = new Player[2];
        players[0] = new AIPlayer("Delano", Colors.BLACK, new MinimaxAlgorithm());
        players[1] = new Player("Lorenzo", Colors.WHITE, true);
        TicTacToe game = new TicTacToe(new TicTacToeBoard(3), players, 10);
        AIPlayer AI = (AIPlayer)players[0];
//        Tile tile = AI.calculateMove(game.getBoard());
//        game.doMove(tile.getRow(), tile.getCol());

        System.out.println("Turn 0 " + game.getCurrentPlayer().getColor().toString());
        game.doMove(0, 2);
        game.getBoard().printBoard();
        if(game.finished) {
            showResult(game);
            System.exit(0);
        }

        System.out.println("Turn 1 " + game.getCurrentPlayer().getColor().toString());
        game.doMove(0, 1);
        game.getBoard().printBoard();
        if(game.finished) {
            showResult(game);
            System.exit(0);
        }

        System.out.println("Turn 2 " + game.getCurrentPlayer().getColor().toString());
        game.doMove(1, 2);
        game.getBoard().printBoard();
        if(game.finished) {
            showResult(game);
            System.exit(0);
        }
        System.out.println("Turn 3 " + game.getCurrentPlayer().getColor().toString());
        game.doMove(1, 1);
        game.getBoard().printBoard();
        if(game.finished) {
            showResult(game);
            System.exit(0);
        }


        System.out.println("Turn 4 " + game.getCurrentPlayer().getColor().toString());
        game.doMove(2, 2);
        game.getBoard().printBoard();
        if(game.finished) {
            showResult(game);
            System.exit(0);
        }

        AI.calculateMove(game.getBoard());

        System.out.println("Turn 5 " + game.getCurrentPlayer().getColor().toString());
        game.doMove(2, 1);
        game.getBoard().printBoard();
        if(game.finished) {
            showResult(game);
            System.exit(0);
        }
    }

    public void showResult(Game game) {
        game.getGameResult();
        System.out.println("GameFinished Player: " + game.getWinner() + " Won!");
    }
}