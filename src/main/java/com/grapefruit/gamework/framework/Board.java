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

    int[][] strat;
    /**
     * Creates a board of given size with strategicValues given from the game implmentation. (TODO)
     */
    private void initGrid() {
        initStrat();
        grid = new Tile[boardSize][boardSize];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                grid[i][j] = new Tile(i, j, strat[i][j]);
            }
        }
    }

    private void initStrat() {
        strat = new int[8][8];
        strat[0][0] = 99;
        strat[0][1] = -8;
        strat[0][2] = 8;
        strat[0][3] = 6;
        strat[0][4] = 6;
        strat[0][5] = 8;
        strat[0][6] = -8;
        strat[0][7] = 99;

        strat[1][0] = -8;
        strat[1][1] = -24;
        strat[1][2] = -4;
        strat[1][3] = -3;
        strat[1][4] = -3;
        strat[1][5] = -4;
        strat[1][6] = -24;
        strat[1][7] = -8;

        strat[2][0] = 8;
        strat[2][1] = -4;
        strat[2][2] = 7;
        strat[2][3] = 4;
        strat[2][4] = 4;
        strat[2][5] = 7;
        strat[2][6] = -4;
        strat[2][7] = 8;

        strat[3][0] = 6;
        strat[3][1] = -3;
        strat[3][2] = 4;
        strat[3][3] = 0;
        strat[3][4] = 0;
        strat[3][5] = 4;
        strat[3][6] = -3;
        strat[3][7] = 6;

        strat[4][0] = 6;
        strat[4][1] = -3;
        strat[4][2] = 4;
        strat[4][3] = 0;
        strat[4][4] = 0;
        strat[4][5] = 4;
        strat[4][6] = -3;
        strat[4][7] = 6;

        strat[5][0] = 8;
        strat[5][1] = -4;
        strat[5][2] = 7;
        strat[5][3] = 4;
        strat[5][4] = 4;
        strat[5][5] = 7;
        strat[5][6] = -4;
        strat[5][7] = 8;

        strat[6][0] = -8;
        strat[6][1] = -24;
        strat[6][2] = -4;
        strat[6][3] = -3;
        strat[6][4] = -3;
        strat[6][5] = -4;
        strat[6][6] = -24;
        strat[6][7] = -8;

        strat[7][0] = 99;
        strat[7][1] = -8;
        strat[7][2] = 8;
        strat[7][3] = 6;
        strat[7][4] = 6;
        strat[7][5] = 8;
        strat[7][6] = -8;
        strat[7][7] = 99;

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
    
    public Tile[][] getGrid() {
        return grid;
    }
    
    public Tile getTile(int row, int col) {
        return grid[row][col];
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
        System.out.println("AVAILABLE MOVES IN PRINTER: "+ moves.size());
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

    public void printAvailableMoves(List<Tile> moves) {
        System.out.println("AVAILABLE MOVES IN PRINTER: "+ moves.size());
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

    public void printAvailableMovesWithStrategicValues(List<Tile> moves) {
        System.out.println("AVAILABLE MOVES IN PRINTER: "+ moves.size());
        System.out.print("  ");
        for (int m = 0; m < grid.length; m++) {
            System.out.print("  " + m + "   ");
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
