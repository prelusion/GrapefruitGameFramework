package com.grapefruit.gamework.framework;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Board.
 */
public abstract class Board {

    /**
     * The constant relativeNeighborGrid.
     */
    protected static final int[][] relativeNeighborGrid = new int[][]{
            {-1, -1}, {-1, 0}, {-1, 1},
            {0, -1}, {0, 1},
            {1, -1}, {1, 0}, {1, 1},
    };

    /**
     * This grid is the board. Size is dynamic so instantiated in the constructor.
     */
    public Tile[][] grid;

    /**
     * The constant BOARDSIZE.
     */
    protected static int BOARDSIZE;

    private int[][] strategicValues;
    /**
     * The Scores.
     */
    public ObservableMap<Player, Integer> scores = FXCollections.observableHashMap();

    /**
     * Instantiates a new Board.
     *
     * @param boardSize       the board size
     * @param strategicValues the strategic values
     */
    public Board(int boardSize, int[][] strategicValues) {
        this.BOARDSIZE = boardSize;
        this.strategicValues = strategicValues;
        initGrid();
    }

    /**
     * Instantiates a new Board.
     *
     * @param boardSize the board size
     */
    public Board(int boardSize) {
        this.BOARDSIZE = boardSize;
        strategicValues = new int[boardSize][boardSize];
        for (int[] row : strategicValues) {
            Arrays.fill(row, 1);
        }
        initGrid();
    }

    /**
     * Get strategic values int [ ] [ ].
     *
     * @return the int [ ] [ ]
     */
    public int[][] getStrategicValues() {
        return strategicValues;
    }

    private void initGrid() {
        grid = new Tile[BOARDSIZE][BOARDSIZE];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                grid[i][j] = new Tile(i, j, strategicValues[i][j]);
            }
        }
    }

    /**
     * Copy state.
     *
     * @param otherBoard the other board
     */
    public void copyState(Board otherBoard) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                grid[i][j].setPlayer(otherBoard.getPlayer(i, j));
            }
        }
    }

    /**
     * Sets strategic values.
     *
     * @param strategic the strategic
     */
    public void setStrategicValues(int[][] strategic) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                grid[i][j].setStrategicValue(strategic[i][j]);
            }
        }
    }

    /**
     * Gets board size.
     *
     * @return the board size
     */
    public int getBoardSize() {
        return BOARDSIZE;
    }

    /**
     * Sets move.
     *
     * @param row    the row
     * @param col    the col
     * @param player the player
     */
    public abstract void setMove(int row, int col, Player player);

    /**
     * Gets available moves.
     *
     * @param player the player
     * @return the available moves
     */
    public abstract List<Tile> getAvailableMoves(Player player);

    /**
     * Sets player.
     *
     * @param row    the row
     * @param col    the col
     * @param player the player
     */
    public void setPlayer(int row, int col, Player player) {
        grid[row][col].setPlayer(player);
    }

    /**
     * Gets player.
     *
     * @param row the row
     * @param col the col
     * @return the player
     */
    public Player getPlayer(int row, int col) {
        return grid[row][col].getPlayer();
    }

    /**
     * Gets tile.
     *
     * @param row the row
     * @param col the col
     * @return the tile
     */
    public Tile getTile(int row, int col) {
        return grid[row][col];
    }


    /**
     * Has player boolean.
     *
     * @param row the row
     * @param col the col
     * @return boolean, Checks if the tile chosen has a piece on it.
     */
    public boolean hasPlayer(int row, int col) {
        if (!isValidLocation(row, col)) {
            return false;
        }
        return grid[row][col].getPlayer() != null;
    }

    /**
     * Is board full boolean.
     *
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

    /**
     * Is valid location boolean.
     *
     * @param row the row
     * @param col the col
     * @return the boolean
     */
    public boolean isValidLocation(int row, int col) {
        return row < grid.length && row >= 0 && col < grid.length && col >= 0;
    }

    /**
     * Count pieces map.
     *
     * @return the map
     */
    public Map<Player, Integer> countPieces() {
        HashMap<Player, Integer> pieces = new HashMap<>();

        for (Tile[] row : grid) {
            for (Tile tile : row) {
                pieces.merge(tile.getPlayer(), 1, Integer::sum);
            }
        }

        return pieces;
    }

    /**
     * Count pieces int.
     *
     * @param player the player
     * @return the int
     */
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
     * Any moves left boolean.
     *
     * @param players the players
     * @return the boolean
     */
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
     *
     * @param player the player
     */
    public void printAvailableMoves(Player player) {
        printAvailableMoves(getAvailableMoves(player));
    }

    /**
     * Print available moves.
     *
     * @param moves the moves
     */
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

    /**
     * Print available moves with strategic values.
     *
     * @param moves the moves
     */
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

    /**
     * Calculate scores.
     *
     * @param players the players
     */
    public abstract void calculateScores(Player[] players);

    /**
     * Empty tiles int.
     *
     * @return the int
     */
    public int emptyTiles() {
        int amount = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                if (grid[i][j].getPlayer() == null) amount++;
            }
        }
        return amount;
    }
}
