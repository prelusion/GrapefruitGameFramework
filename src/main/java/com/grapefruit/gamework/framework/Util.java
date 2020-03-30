package com.grapefruit.gamework.framework;

public abstract class Util {

    public static final int[][] outerGrid = getOuterGrid();

    private static int[][] getOuterGrid(){
        int[][] coords = new int[8][2];

        coords[0][0] = -1;
        coords[0][1] = -1;

        coords[1][0] = -1;
        coords[1][1] = 0;

        coords[2][0] = -1;
        coords[2][1] = 1;

        coords[3][0] = 0;
        coords[3][1] = -1;

        coords[4][0] = 0;
        coords[4][1] = 1;

        coords[5][0] = 1;
        coords[5][1] = -1;

        coords[6][0] = 1;
        coords[6][1] = 0;

        coords[7][0] = 1;
        coords[7][1] = 1;
        
        return coords;
    }
}
