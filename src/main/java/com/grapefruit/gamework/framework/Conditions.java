package com.grapefruit.gamework.framework;

public final class Conditions {
    public static boolean checkAllAdjacentConditions(Board board) {
        return checkFullRow(board) || checkFullColumn(board) || checkFullDiagonal(board);
    }
    public static Tile getTileOfAvailableConditions(Board board) {
        Tile tile = null;
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
