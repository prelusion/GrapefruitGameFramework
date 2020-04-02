package com.grapefruit.gamework.framework.network;

import org.junit.Test;

/**
 * This unittest tests wether some functions in the Commands class work properly.
 */
public class CommandsTest {

    /**
     * Run test.
     */
    @Test
    public void runTest() {
        assert  setMove(4, 5, 9).equals("move 42");
        assert challenge(true).equals("accept");
        assert challenge(false).equals("decline");
    }


    /**
     * Tests setMove.
     *
     * @param row       int the row on the board.
     * @param col       int the col on the board.
     * @param boardSize int the board size.
     * @return int the index
     */
    public static String setMove(int row, int col, int boardSize) {
        return "move " + Integer.toString((int)row * boardSize + col + 1);
    }

    /**
     * Tests challenge.
     *.
     * @param accept boolean Flag to indicate if the challenge is accepted or not.
     * @return String result.
     */
    public static String challenge(boolean accept) {
        if (accept) {
            return "accept";
        } else {
            return "decline";
        }
    }
}