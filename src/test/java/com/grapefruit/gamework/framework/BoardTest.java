package com.grapefruit.gamework.framework;

import com.grapefruit.gamework.games.tictactoe.TicTacToe;
import com.grapefruit.gamework.games.tictactoe.TicTacToeBoard;
import org.junit.Test;

public class BoardTest {
    @Test
    public void printBoard() {
        Player[] players = new Player[2];
        players[0] = new Player("Delano", Colors.BLACK);
        players[1] = new Player("Lorenzo", Colors.WHITE);
        TicTacToe game = new TicTacToe(new TicTacToeBoard(3), players, 10);
        game.nextPlayer();
        
        System.out.println("Turn 0 " + game.getCurrentPlayer().getColor().toString());
        game.setMove(0, 2);
        game.nextPlayer();
        game.getBoard().printBoard();
        if(game.finished) {
            showResult(game);
            System.exit(0);
        }

        System.out.println("Turn 1 " + game.getCurrentPlayer().getColor().toString());
        game.setMove(0, 1);
        game.nextPlayer();
        game.getBoard().printBoard();
        if(game.finished) {
            showResult(game);
            System.exit(0);
        }

        System.out.println("Turn 2 " + game.getCurrentPlayer().getColor().toString());
        game.setMove(1, 2);
        game.nextPlayer();
        game.getBoard().printBoard();
        if(game.finished) {
            showResult(game);
            System.exit(0);
        }
        System.out.println("Turn 3 " + game.getCurrentPlayer().getColor().toString());
        game.setMove(1, 1);
        game.nextPlayer();
        game.getBoard().printBoard();
        if(game.finished) {
            showResult(game);
            System.exit(0);
        }


        System.out.println("Turn 4 " + game.getCurrentPlayer().getColor().toString());
        game.setMove(2, 2);
        game.nextPlayer();
        game.getBoard().printBoard();
        if(game.finished) {
            showResult(game);
            System.exit(0);
        }


        System.out.println("Turn 5 " + game.getCurrentPlayer().getColor().toString());
        game.setMove(2, 1);
        game.nextPlayer();
        game.getBoard().printBoard();
        if(game.finished) {
            showResult(game);
            System.exit(0);
        }
    }

    public void showResult(Game game) {
        System.out.println("GameFinished Player: " + game.getWinner() + " Won!");
    }
}