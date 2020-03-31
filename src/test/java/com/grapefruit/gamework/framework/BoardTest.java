package com.grapefruit.gamework.framework;

import com.grapefruit.gamework.games.tictactoe.TicTacToe;
import org.junit.Test;

public class BoardTest {
    @Test
    public void printBoard() {
        Player[] players = new Player[2];
        players[0] = new Player("X", "Xross");
        players[1] = new Player("C", "Circle");
        players[0].setTurn(true);
        TicTacToe game = new TicTacToe(new Board(3), players, 10);

        System.out.println("Turn 0");
        game.setPlayersTurn(players[0]);
        game.setMove(0, 2);
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