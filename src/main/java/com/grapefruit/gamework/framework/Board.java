package com.grapefruit.gamework.framework;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Board {

    protected static final int[][] relativeNeighborGrid = new int[][] {
            {-1, -1}, {-1, 0}, {-1, 1},
            {0, -1}, {0, 1},
            {1, -1}, {1, 0}, {1, 1},
    };

    /**
     * This grid is the board. Size is dynamic so instantiated in the constructor.
     */
    protected Tile[][] grid;

    protected int boardSize;

    private int[][] strategicValues;

    public Board(int boardSize, int[][] strategicValues) {
        this.boardSize = boardSize;
        this.strategicValues = strategicValues;
        initGrid();
    }

    public Board(int boardSize) {
        strategicValues = new int[boardSize][boardSize];
        for (int[] row : strategicValues) {
            Arrays.fill(row, 1);
        }
        initGrid();
    }

    private void initGrid() {
        grid = new Tile[boardSize][boardSize];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                grid[i][j] = new Tile(i, j, strategicValues[i][j]);
            }
        }
    }

    public void copyState(Board otherBoard) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                grid[i][j].setPlayer(otherBoard.getPlayer(i, j));
            }
        }
    }

    public int getBoardSize() {
        return boardSize;
    }

    public abstract void setMove(int row, int col, Player player);

    public abstract List<Tile> getAvailableMoves(Player player);

    public void setPlayer(int row, int col, Player player) {
        grid[row][col].setPlayer(player);
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
                if (tiles[j].getPlayer() == null) return false;
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

    public boolean anyMovesLeft(Player[] players) {
        return Arrays.stream(players)
                .noneMatch(player -> (getAvailableMoves(player).size()) > 0);
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
     * Helper function to print the board.
     */
    public void printStrategicValues() {
        System.out.print("  ");
        for (int m = 0; m < grid.length; m++) {
            System.out.print(" " + m + "  ");
        }
        System.out.println();

        for (int i = 0; i < grid.length; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < grid.length; j++) {
                String value = String.format("[%3s]", grid[i][j].getStrategicValue());
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
        printAvailableMoves(getAvailableMoves(player));
    }

    public void printAvailableMoves(List<Tile> moves) {
        for (int m = 0; m < grid.length; m++)
            System.out.print(" " + m + "  ");
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

    public void printAvailableMovesWithStrategicValues(List<Tile> moves) {
        for (int m = 0; m < grid.length; m++)
            System.out.print("  " + m + "   ");
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
                    value = String.format("[%3s]", tileFound.getStrategicValue());
                } else if (positionPlayer == null) {
                    value = "[   ]";
                } else {
                    value = String.format("[%3s]", positionPlayer.getColor().toString().charAt(0));
                }

                System.out.print(value + " ");
            }
            System.out.println();
        }
    }
}
