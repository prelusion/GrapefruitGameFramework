package com.grapefruit.gamework.framework;

import org.junit.Test;

public class BoardTest {
    @Test
    public void printBoard() {
        Board board = new Board(8);
        Player playerA = new Player("A", "red");
        Player playerB = new Player("A", "blue");
        board.setPiece(2, 3, playerA);
        board.setPiece(4, 1, playerB);
        board.setPiece(4, 3, playerB);
        board.printBoard();
    }
}