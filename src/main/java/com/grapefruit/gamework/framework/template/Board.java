package com.grapefruit.gamework.framework.template;

public class Board {

    /**
     * This grid is the board. Size is dynamic so instantiated in the constructor.
     */
    private Tile[][] grid;

    /**
     * Constructor for making a new Board object.
     * @param int, boardSize is given to give the board grid a size.
     */
    public Board(int boardSize) {
        grid = new Tile[boardSize][boardSize];
        createBoard();
    }

    /**
     * Creates a board of given size with strategicValues given from the game implmentation. (TODO)
     */
    public void createBoard() {
        for (int x = 0; x < grid.length-1; x++) {
            for (int y = 0; y < grid.length-1; y++) {
                grid[x][y] = new Tile(x, y, /* TODO  */ 0);
            }
        }
    }

    /**
     * @return Tile[][], Get the current board with tiles.
     */
    public Tile[][] getGrid() {
        return grid;
    }

    public void setPiece(Tile tile) {

    }

    /**
     * @return boolean, Checks if the tile chosen has a piece on it.
     */
    public boolean hasPiece(Tile tile) { return tile.getPlayer() != null; }

    /**
     * @return boolean, Checks if the board is full. Return true if it is.
     */
    public boolean isBoardFull() {
        for (int x = 0; x < grid.length-1; x++) {
            for (int y = 0; y < grid.length-1; y++) {
                if(grid[x][y] == null) {
                    return false;
                }
            }
        }
        return true;
    }

    public String getPieceString(int row, int col) {
        return grid[row][col].getPlayer().toString();
    }

}
