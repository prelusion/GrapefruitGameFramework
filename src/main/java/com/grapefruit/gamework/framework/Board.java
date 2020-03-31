package com.grapefruit.gamework.framework;

import java.util.HashSet;

public abstract class Board {

    /**
     * This grid is the board. Size is dynamic so instantiated in the constructor.
     */
    protected Tile[][] grid;
    protected int boardSize;
    protected static final int[][] relativeNeighborGrid = initRelativeNeighborGrid();

    /**
     * Constructor for making a new Board object.
     *
     * @param boardSize, boardSize is given to give the board grid a size.
     */
    public Board(int boardSize) {
        this.boardSize = boardSize;
        grid = new Tile[boardSize][boardSize];
        createBoard();
    }

    /**
     * Creates a board of given size with strategicValues given from the game implmentation. (TODO)
     */
    public void createBoard() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                grid[i][j] = new Tile(i, j, /* TODO  */ 0);
            }
        }
    }

    public Tile[][] getGrid() {
        return grid;
    }

    public void setPlayer(int row, int col, Player player) {
        grid[row][col] = new Tile(row, col, 1, player);
    }

    /**
     * @return boolean, Checks if the tile chosen has a piece on it.
     */
    public boolean hasPlayer(int row, int col) {
        return grid[row][col].getPlayer() != null;
    }

    /**
     * @return boolean, Checks if the board is full. Return true if it is.
     */
    public boolean isBoardFull() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                if (grid[i][j].getPlayer() == null) {
                    return false;
                }
            }
        }
        return true;
    }

    public String getPlayerName(int row, int col) {
        return grid[row][col].getPlayer().toString();
    }

    public boolean isValidLocation(int row, int col) {
        return row >= 0 && row <= boardSize && col >= 0 && col <= boardSize;
    }

    /**
     * Helper function
     */
    public void printBoard() {
        System.out.print("  ");
        for (int m = 0; m < grid.length; m++) {
            System.out.print(" " + m + "  ");
        }
        System.out.println();

        for (int i = 0; i < grid.length; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < grid.length; j++) {
                Player player = grid[i][j].getPlayer();
                String value;
                if (player == null) {
                    value = "[ ]";
                } else {
                    value = String.format("[%s]", player.getColor().toString().charAt(0));
                }

                System.out.print(value + " ");
            }
            System.out.println();
        }
    }

    public void printAvailableMoves(Player player) {
        HashSet<Tile> moves = getAvailableMoves(player);

        System.out.print("  ");
        for (int m = 0; m < grid.length; m++) {
            System.out.print(" " + m + "  ");
        }
        System.out.println();

        for (int i = 0; i < grid.length; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < grid.length; j++) {
                Player positionPlayer = grid[i][j].getPlayer();
                String value;

                Tile tileFound = null;

                for (Tile tile : moves) {
                    if (tile.getRow() == i && tile.getCol() == j) {
                        tileFound = tile;
                    }
                }

                if (tileFound != null) {
                    value = "[*]";
                } else if (positionPlayer == null) {
                    value = "[ ]";
                } else {
                    value = String.format("[%s]", positionPlayer.getColor().toString().charAt(0));
                }

                System.out.print(value + " ");
            }
            System.out.println();
        }
    }

    public abstract HashSet<Tile> getAvailableMoves(Player player);

    public Player getPlayer(int row, int col) {
        return grid[row][col].getPlayer();
    }

    private static int[][] initRelativeNeighborGrid() {
        int[][] coords = new int[8][2];

        /* top left */
        coords[0][0] = -1;
        coords[0][1] = -1;

        /* top center */
        coords[1][0] = -1;
        coords[1][1] = 0;

        /* top right */
        coords[2][0] = -1;
        coords[2][1] = 1;

        /* mid left */
        coords[3][0] = 0;
        coords[3][1] = -1;

        /* mid right */
        coords[4][0] = 0;
        coords[4][1] = 1;

        /* bottm left */
        coords[5][0] = 1;
        coords[5][1] = -1;

        /* bottom center */
        coords[6][0] = 1;
        coords[6][1] = 0;

        /* bottom right */
        coords[7][0] = 1;
        coords[7][1] = 1;

        return coords;
    }
}
