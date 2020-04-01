package com.grapefruit.gamework.framework;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.grapefruit.gamework.framework.player.Player;

public abstract class Board {

    /**
     * This grid is the board. Size is dynamic so instantiated in the constructor.
     */
    protected Tile[][] grid;

    protected int boardSize;

    protected static final int[][] relativeNeighborGrid = new int[][] {
            {-1, -1}, {-1, 0}, {-1, 1},
            {0, -1}, {0, 1},
            {1, -1}, {1, 0}, {1, 1},
    };

    /**
     * Constructor for making a new Board object.
     *
     * @param boardSize, boardSize is given to give the board grid a size.
     */
    public Board(int boardSize) {
        this.boardSize = boardSize;
        initGrid();
    }

    /**
     * Creates a board of given size with strategicValues given from the game implmentation. (TODO)
     */
    private void initGrid() {
        grid = new Tile[boardSize][boardSize];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                grid[i][j] = new Tile(i, j, /* TODO  */ 0);
            }
        }
    }

    /* TODO remove getGrid because the grid should be private. */
    public Tile[][] getGrid() {
        return grid;
    }

    public abstract void setMove(int row, int col, Player player);

    public abstract List<Tile> getAvailableMoves(Player player);

    public void setPlayer(int row, int col, Player player) {
        grid[row][col] = new Tile(row, col, 1, player);
    }

    public Player getPlayer(int row, int col) {
        return grid[row][col].getPlayer();
    }

    /**
     * @return boolean, Checks if the tile chosen has a piece on it.
     */
    public boolean hasPlayer(int row, int col) {
        if (!isValidLocation(row, col)) {
            return false;
        }
        return grid[row][col].getPlayer() != null;
    }

    /**
     * @return boolean, Checks if the board is full. Return true if it is.
     */
    public boolean isBoardFull() {
        for (Tile[] tiles : grid) {
            for (int j = 0; j < grid.length; j++) {
                if (tiles[j].getPlayer() == null) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isValidLocation(int row, int col) {
        return row < grid.length && row >= 0 && col < grid.length && col >= 0;
    }

    public Map<Player, Integer> countPieces() {
        HashMap<Player, Integer> pieces = new HashMap<>();

        for (Tile[] row : grid) {
            for (Tile tile : row) {
                pieces.merge(tile.getPlayer(), 1, Integer::sum);
            }
        }

        return pieces;
    }

    public int countPieces(Player player) {
        int count = 0;

        for (Tile[] row : grid) {
            for (Tile tile : row) {
                if (tile.getPlayer() == player) count++;
            }
        }

        return count;
    }

    /**
     * Helper function to print the board.
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

    /**
     * Helper function to print available moves for a player.
     * @param player
     */
    public void printAvailableMoves(Player player) {
        List<Tile> moves = getAvailableMoves(player);

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
}
