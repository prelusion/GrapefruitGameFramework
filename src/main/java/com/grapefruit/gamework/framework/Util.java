package com.grapefruit.gamework.framework;

public abstract class Util {

    public static final int[][] outerGrid = getOuterGrid();

    private static int[][] getOuterGrid(){
        int[][] coords = new int[8][2];

        coords[0][0] = -1;
        coords[0][1] = -1;

        coords[0][0] = -1;
        coords[0][0] = 0;

        coords[0][0] = -1;
        coords[0][0] = 1;

        coords[0][0] = 0;
        coords[0][0] = -1;

        coords[0][0] = 0;
        coords[0][0] = 1;

        coords[0][0] = 1;
        coords[0][0] = -1;

        coords[0][0] = 1;
        coords[0][0] = 0;

        coords[0][0] = 1;
        coords[0][0] = 1;
        
        return coords;
    }
}
