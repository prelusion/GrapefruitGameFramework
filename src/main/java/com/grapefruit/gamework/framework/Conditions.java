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
        boolean fullRow;
        for (int row = 0; row < board.getBoardSize(); row++) {
            fullRow = true;
            for (int col = 1; col < board.getBoardSize(); col++) {
                if(board.getTile(row, col).getPlayer() == null) {
                    fullRow = false;
                } else {
                    if (!board.getTile(row, col).getPlayer().equals(board.getTile(row, col - 1).getPlayer())) {
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
//        Tile[][] grid = board.getGrid();
        boolean fullRow;
        for (int row = 0; row < board.getBoardSize(); row++) {
            fullRow = true;
            for (int col = 1; col < board.getBoardSize(); col++) {
                if(board.getTile(row, col).getPlayer() == null) {
                    fullRow = false;
                } else {
                    if (!board.getTile(row, col).getPlayer().equals(board.getTile(row, col - 1).getPlayer())) {
                        fullRow = false;
                    }
                }
            }
            if (fullRow) {
                return board.getTile(row, 0);
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
        boolean fullColumn;
        for (int col = 0; col < board.getBoardSize(); col++) {
            fullColumn = true;
            for (int row = 1; row < board.getBoardSize(); row++) {
                if(board.getTile(row, col).getPlayer() == null) {
                    fullColumn = false;
                } else {
                    if (!board.getTile(row, col).getPlayer().equals(board.getTile(row - 1, col).getPlayer())) {
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
        boolean fullColumn;
        for (int col = 0; col < board.getBoardSize(); col++) {
            fullColumn = true;
            for (int row = 1; row < board.getBoardSize(); row++) {
                if(board.getTile(row, col).getPlayer() == null) {
                    fullColumn = false;
                } else {
                    if (!board.getTile(row, col).getPlayer().equals(board.getTile(row - 1, col).getPlayer())) {
                        fullColumn = false;
                    }
                }
            }
            if (fullColumn) {
                return board.getTile(0, col);
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
        boolean firstFullDiagonal = true;
        int col;
        for (int row = 1; row < board.getBoardSize(); row++) {
            col = row;
            if(board.getTile(row - 1, col - 1).getPlayer() == null) {
                firstFullDiagonal = false;
            } else {
                if (!board.getTile(row - 1, col - 1).getPlayer().equals(board.getTile(row, col).getPlayer())) {
                    firstFullDiagonal = false;
                }
            }
        }
        if (firstFullDiagonal) {
            return firstFullDiagonal;
        }

        boolean secondFullDiagonal = true;
        for (int row = board.getBoardSize() - 1; row > 0; row--) {
            col = (row % (board.getBoardSize() - 1));
            if(board.getTile(row, col).getPlayer() == null) {
                secondFullDiagonal = false;
            } else {
                if (!board.getTile(row, col).getPlayer().equals(board.getTile(row - 1, col + 1).getPlayer())) {
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
        boolean firstFullDiagonal = true;
        int col;
        for (int row = 1; row < board.getBoardSize(); row++) {
            col = row;
            if(board.getTile(row - 1, col - 1).getPlayer() == null) {
                firstFullDiagonal = false;
            } else {
                if (!board.getTile(row - 1, col - 1).getPlayer().equals(board.getTile(row, col).getPlayer())) {
                    firstFullDiagonal = false;
                }
            }
        }
        if (firstFullDiagonal) {
            return board.getTile(0, 0);
        }


        boolean secondFullDiagonal = true;
        for (int row = board.getBoardSize()- 1; row > 0; row--) {
            col = (row % (board.getBoardSize() - 1));
            if(board.getTile(row, col).getPlayer() == null) {
                secondFullDiagonal = false;
            } else {
                if (!board.getTile(row, col).getPlayer().equals(board.getTile(row - 1, col + 1).getPlayer())) {
                    secondFullDiagonal = false;
                }
            }
        }
        if (secondFullDiagonal) {
            return board.getTile(board.getBoardSize() - 1, 0);
        }
        return null;
    }
}
