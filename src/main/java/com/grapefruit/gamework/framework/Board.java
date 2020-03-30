package com.grapefruit.gamework.framework;

public class Board {

    /**
     * This grid is the board. Size is dcolnamic so instantiated in the constructor.
     */
    private Tile[][] grid;

    /**
     * Constructor for making a new Board object.
     * @param boardSize, boardSize is given to give the board grid a size.
     */
    public Board(int boardSize) {
        grid = new Tile[boardSize][boardSize];
        createBoard();
    }

    /**
     * Creates a board of given size with strategicValues given from the game implmentation. (TODO)
     */
    public void createBoard() {
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid.length; col++) {
                grid[row][col] = new Tile(row, col, /* TODO  */ 0);
            }
        }
    }

    /**
     * @return Tile[][], Get the current board with tiles.
     */
    public Tile[][] getGrid() {
        return grid;
    }


    /**
     * @return boolean, Checks if the tile chosen has a piece on it.
     */
    public boolean hasPiece(int row, int col) {
        if (isValidLocation(row, col)){
            return grid[row][col].getPiece() != null;
        }
        else {
            return false;
        }
    }

    public boolean isValidLocation(int row, int col){
        if (grid.length > row - 1) {
            if (grid[row].length > col - 1) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return boolean, Checks if the board is full. Return true if it is.
     */
    public boolean isBoardFull() {
        for (int row = 0; row < grid.length-1; row++) {
            for (int col = 0; col < grid.length-1; col++) {
                if(grid[row][col] == null) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * @param row is the row of the chosen Tile.
     * @param col is the col of the chosen Tile.
     * @return String, Gets the string of the piece string name.
     */
    public String getPieceString(int row, int col) {
        return grid[row][col].getPiece().toString();
    }

}
