package com.grapefruit.gamework.network;

public class Helpers {
    /**
     * Convert a move string received from the server to an array containing the row and column of a matrix.
     * @param move
     * @param boardSize
     * @return row and column in a matrix
     */
    public static int[] convertMoveString(String move, int boardSize) {
        int index = Integer.parseInt(move);

        int row = index / boardSize;
        int col = index % boardSize;

        return new int[]{row, col};
    }
}
