package com.grapefruit.gamework.framework;

public final class Conditions {
    /**
     * This are global game conditions.
     * These conditions can be given to any game that require such a rules.
     * For example the implementation has a function that checks if a row, column or diagonal row is full of pieces.
     * This is a condition that is a must have within TicTacToe and Bingo. So this rule is made here.
     */

    /**
     * Checks whether a row, column or diagonal has adjacent pieces which are all the same.
     * @return boolean, true if there is a row, column or diagonal row is full of pieces.
     */
    public static boolean checkAllAdjacentConditions(Board board) {
        return checkFullRow(board) || checkFullColumn(board) || checkFullDiagonal(board);
    }

    /**
     * Checks whether a row, column or diagonal has adjacent pieces which are all the same.
     * @return Tile, the tile where the functions condition has been met. The tile is the index value of the row.
     */
    public static Tile getTileOfAvailableConditions(Board board) {
        if(getTileFullRow(board) != null) {
            return getTileFullRow(board);
        }
        if(getTileFullColumn(board) != null) {
            return getTileFullColumn(board);
        }
        if(getTileFullDiagonal(board) != null) {
            return getTileFullDiagonal(board);
        }
        return null;
    }

    /**
     * Checks whether a row has adjacent pieces which are all the same.
     * @return boolean, true when a row is full of pieces.
     */
    public static boolean checkFullRow(Board board) {
        Tile[][] grid = board.getGrid();
        boolean fullRow;
        for (int x = 0; x < grid.length -1; x++) {
            fullRow = true;
            for (int y = 0; y < grid.length -1; y++) {
                if (y > 0 && !grid[x][y].getPiece().getType().equals(grid[x][y-1].getPiece().getType())) {
                    fullRow = false;
                }
            }
            if(fullRow) {
                return true;
            }
        }
        return false;
    }
    /**
     * Checks whether a row has adjacent pieces which are all the same.
     * @return Tile, true when a row is full of pieces.
     */
    public static Tile getTileFullRow(Board board) {
        Tile[][] grid = board.getGrid();
        boolean fullRow;
        for (int x = 0; x < grid.length -1; x++) {
            fullRow = true;
            for (int y = 0; y < grid.length -1; y++) {
                if (y > 0 && !grid[x][y].getPiece().getType().equals(grid[x][y-1].getPiece().getType())) {
                    fullRow = false;
                }
            }
            if(fullRow) {
                return grid[x][0];
            }
        }
        return null;
    }

    /**
     * Checks whether a column has adjacent pieces which are all the same.
     * @return boolean, true when a column is full of pieces.
     */
    public static boolean checkFullColumn(Board board) {
        Tile[][] grid = board.getGrid();
        boolean fullColumn;
        for (int y = 0; y < grid.length -1; y++) {
            fullColumn = true;
            for (int x = 0; x < grid.length -1; x++) {
                if (x > 0 && !grid[x][y].getPiece().getType().equals(grid[x-1][y].getPiece().getType())) {
                    fullColumn = false;
                }
            }
            if(fullColumn) {
                return true;
            }
        }
        return false;
    }
    /**
     * Checks whether a column has adjacent pieces which are all the same.
     * @return Tile, true when a column is full of pieces.
     */
    public static Tile getTileFullColumn(Board board) {
        Tile[][] grid = board.getGrid();
        boolean fullColumn;
        for (int y = 0; y < grid.length -1; y++) {
            fullColumn = true;
            for (int x = 0; x < grid.length -1; x++) {
                if (x > 0 && !grid[x][y].getPiece().getType().equals(grid[x-1][y].getPiece().getType())) {
                    fullColumn = false;
                }
            }
            if(fullColumn) {
                return grid[0][y];
            }
        }
        return null;
    }

    /**
     * Checks whether a diagonal has adjacent pieces which are all the same.
     * @return boolean, true when a diagonal is full of pieces.
     */
    public static boolean checkFullDiagonal(Board board) {
        Tile[][] grid = board.getGrid();
        boolean fullDiagonal = true;
        int y;
        for (int x = 0; x < grid.length -1; x++) {
            y = x;
            if (x > 0 && !grid[x-1][y-1].getPiece().getType().equals(grid[x][y].getPiece().getType())) {
                fullDiagonal = false;
            }
        }
        if (fullDiagonal)  {
            return fullDiagonal;
        }

        for (int x = grid.length-1; x > 0; x--) {
            y = x;
            if (x < grid.length -1 && !grid[x+1][y+1].getPiece().getType().equals(grid[x][y].getPiece().getType())) {
                fullDiagonal = false;
            }
            if (fullDiagonal)  {
                return fullDiagonal;
            }
        }
        return fullDiagonal;
    }
    /**
     * Checks whether a diagonal has adjacent pieces which are all the same.
     * @return Tile, true when a diagonal is full of pieces.
     */
    public static Tile getTileFullDiagonal(Board board) {
        Tile[][] grid = board.getGrid();
        boolean fullDiagonal = true;
        int y;
        for (int x = 0; x < grid.length -1; x++) {
            fullDiagonal = true;
            y = x;
            if (x > 0 && !grid[x-1][y-1].getPiece().getType().equals(grid[x][y].getPiece().getType())) {
                fullDiagonal = false;
            }
        }
        if (fullDiagonal)  {
            return grid[0][0];
        }

        for (int x = grid.length-1; x > 0; x--) {
            fullDiagonal = true;
            y = x;
            if (x < grid.length -1 && !grid[x+1][y+1].getPiece().getType().equals(grid[x][y].getPiece().getType())) {
                fullDiagonal = false;
            }
        }
        if (fullDiagonal)  {
            return grid[grid.length -1][0];
        }
        return null;
    }
}
