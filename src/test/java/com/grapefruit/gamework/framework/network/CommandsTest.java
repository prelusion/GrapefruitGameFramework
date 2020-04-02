package com.grapefruit.gamework.framework.network;

import org.junit.Test;

public class CommandsTest {

    @Test
    public void runTest() {
        assert  setMove(4, 5, 9) == 42;
    }


    public static double setMove(int row, int col, int boardSize) {
        double index = row * boardSize + col + 1;
        return index;
    }
}