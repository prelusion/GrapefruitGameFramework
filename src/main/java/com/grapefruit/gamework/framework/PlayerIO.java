package com.grapefruit.gamework.framework;

public interface PlayerIO {
    /**
     * Ask the user to make a move.
     * @return x, y coordinate representing the move.
     */
    int[] askMove();
}
