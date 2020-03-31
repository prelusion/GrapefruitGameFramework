package com.grapefruit.gamework.framework;

import com.grapefruit.gamework.games.reversi.ReversiBoard;
import com.grapefruit.gamework.games.tictactoe.TicTacToe;
import org.junit.Test;

public class BoardTest {
    @Test
    public void printBoard() {
        Board board = new ReversiBoard(8);
        Player playerA = new Player("A", "red");
        Player playerB = new Player("A", "blue");
        board.setPiece(2, 3, playerA);
        board.setPiece(4, 1, playerB);
        board.setPiece(4, 3, playerB);
        board.printBoard();
    }
}
