package com.grapefruit.gamework.framework;

public class Board {

    /**
     * This grid is the board. Size is dynamic so instantiated in the constructor.
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
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid.length; y++) {
                grid[x][y] = new Tile(x, y, /* TODO  */ 0);
            }
        }
    }

    public Tile[][] getGrid() { return grid; }

    public void setPiece(int row, int col, Player player) {
        grid[row][col] = new Tile(row, col, 1, player);
    }

    /**
     * @return boolean, Checks if the tile chosen has a piece on it.
     */
    public boolean hasPiece(int row, int col) {
        return grid[row][col].getPlayer() != null;
    }

    /**
     * @return boolean, Checks if the board is full. Return true if it is.
     */
    public boolean isBoardFull() {
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid.length; y++) {
                if(grid[x][y].getPlayer() == null) {
                    return false;
                }
            }
        }
        return true;
    }

    public String getPieceString(int row, int col) {
        return grid[row][col].getPlayer().toString();
    }

    public boolean isValidLocation(int row, int col) {
        return false;
    }


    /**
     * Helper function
     */
    public void printBoard() {
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid.length; y++) {
                Player player = grid[x][y].getPlayer();
                String value;
                if (player == null) {
                    value = "[ ]";
                } else {
                    value = String.format("[%s]", player.getColor().charAt(0));
                }

                System.out.print(value + " ");
            }
            System.out.println();
        }
    }
}
