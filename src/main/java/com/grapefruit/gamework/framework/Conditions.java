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
     *
     * @return boolean, true if there is a row, column or diagonal row is full of pieces.
     */
    public static boolean checkAllAdjacentConditions(Board board) {
        return checkFullRow(board) || checkFullColumn(board) || checkFullDiagonal(board);
    }

    /**
     * Checks whether a row, column or diagonal has adjacent pieces which are all the same.
     *
     * @return Tile, the tile where the functions condition has been met. The tile is the index value of the row.
     */
    public static Tile getTileOfAvailableConditions(Board board) {
        if (getTileFullRow(board) != null) {
            return getTileFullRow(board);
        }
        if (getTileFullColumn(board) != null) {
            return getTileFullColumn(board);
        }
        if (getTileFullDiagonal(board) != null) {
            return getTileFullDiagonal(board);
        }
        return null;
    }

    /**
     * Checks whether a row has adjacent pieces which are all the same.
     *
     * @return boolean, true when a row is full of pieces.
     */
    public static boolean checkFullRow(Board board) {
        Tile[][] grid = board.getGrid();
        boolean fullRow;
        for (int x = 0; x < grid.length; x++) {
            fullRow = true;
            for (int y = 1; y < grid.length; y++) {
                if(grid[x][y].getPlayer() == null) {
                    fullRow = false;
                } else {
                    if (!grid[x][y].getPlayer().equals(grid[x][y - 1].getPlayer())) {
                        fullRow = false;
                    }
                }
            }
            if (fullRow) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks whether a row has adjacent pieces which are all the same.
     *
     * @return Tile, true when a row is full of pieces.
     */
    public static Tile getTileFullRow(Board board) {
        Tile[][] grid = board.getGrid();
        boolean fullRow;
        for (int x = 0; x < grid.length; x++) {
            fullRow = true;
            for (int y = 1; y < grid.length; y++) {
                if(grid[x][y].getPlayer() == null) {
                    fullRow = false;
                } else {
                    if (!grid[x][y].getPlayer().equals(grid[x][y - 1].getPlayer())) {
                        fullRow = false;
                    }
                }
            }
            if (fullRow) {
                return grid[x][0];
            }
        }
        return null;
    }

    /**
     * Checks whether a column has adjacent pieces which are all the same.
     *
     * @return boolean, true when a column is full of pieces.
     */
    public static boolean checkFullColumn(Board board) {
        Tile[][] grid = board.getGrid();
        boolean fullColumn;
        for (int y = 0; y < grid.length; y++) {
            fullColumn = true;
            for (int x = 1; x < grid.length; x++) {
                if(grid[x][y].getPlayer() == null) {
                    fullColumn = false;
                } else {
                    if (!grid[x][y].getPlayer().equals(grid[x - 1][y].getPlayer())) {
                        fullColumn = false;
                    }
                }
            }
            if (fullColumn) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks whether a column has adjacent pieces which are all the same.
     *
     * @return Tile, true when a column is full of pieces.
     */
    public static Tile getTileFullColumn(Board board) {
        Tile[][] grid = board.getGrid();
        boolean fullColumn;
        for (int y = 0; y < grid.length; y++) {
            fullColumn = true;
            for (int x = 1; x < grid.length; x++) {
                if(grid[x][y].getPlayer() == null) {
                    fullColumn = false;
                } else {
                    if (!grid[x][y].getPlayer().equals(grid[x - 1][y].getPlayer())) {
                        fullColumn = false;
                    }
                }
            }
            if (fullColumn) {
                return grid[0][y];
            }
        }
        return null;
    }

    /**
     * Checks whether a diagonal has adjacent pieces which are all the same.
     *
     * @return boolean, true when a diagonal is full of pieces.
     */
    public static boolean checkFullDiagonal(Board board) {
        Tile[][] grid = board.getGrid();
        boolean firstFullDiagonal = true;
        int y;
        for (int x = 1; x < grid.length; x++) {
            y = x;
            if(grid[x - 1][y - 1].getPlayer() == null) {
                firstFullDiagonal = false;
            } else {
                if (!grid[x - 1][y - 1].getPlayer().equals(grid[x][y].getPlayer())) {
                    firstFullDiagonal = false;
                }
            }
        }
        if (firstFullDiagonal) {
            return firstFullDiagonal;
        }

        boolean secondFullDiagonal = true;
        for (int x = grid.length - 1; x > 0; x--) {
            y = (x % (grid.length - 1));
            if(grid[x][y].getPlayer() == null) {
                secondFullDiagonal = false;
            } else {
                if (!grid[x][y].getPlayer().equals(grid[x - 1][y + 1].getPlayer())) {
                    secondFullDiagonal = false;
                }
            }
        }
        if (secondFullDiagonal) {
            return secondFullDiagonal;
        }
        return secondFullDiagonal;
    }

    /**
     * Checks whether a diagonal has adjacent pieces which are all the same.
     *
     * @return Tile, true when a diagonal is full of pieces.
     */
    public static Tile getTileFullDiagonal(Board board) {
        Tile[][] grid = board.getGrid();
        boolean firstFullDiagonal = true;
        int y;
        for (int x = 1; x < grid.length; x++) {
            y = x;
            if(grid[x - 1][y - 1].getPlayer() == null) {
                firstFullDiagonal = false;
            } else {
                if (!grid[x - 1][y - 1].getPlayer().equals(grid[x][y].getPlayer())) {
                    firstFullDiagonal = false;
                }
            }
        }
        if (firstFullDiagonal) {
            return grid[0][0];
        }


        boolean secondFullDiagonal = true;
        for (int x = grid.length - 1; x > 0; x--) {
            y = (x % (grid.length - 1));
            if(grid[x][y].getPlayer() == null) {
                secondFullDiagonal = false;
            } else {
                if (!grid[x][y].getPlayer().equals(grid[x - 1][y + 1].getPlayer())) {
                    secondFullDiagonal = false;
                }
            }
        }
        if (secondFullDiagonal) {
            return grid[grid.length - 1][0];
        }
        return null;
    }
}
