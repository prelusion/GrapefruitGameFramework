package com.grapefruit.gamework.network;

public class Helpers {
    public static int[] convertMoveString(String move, int boardSize) {
        int index = Integer.parseInt(move);

        int row = index / boardSize;
        int col = index % boardSize;

        return new int[]{row, col};
    }
}
