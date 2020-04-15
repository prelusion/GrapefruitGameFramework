package com.grapefruit.gamework.framework;

/**
 * The type Tile.
 */
public class Tile {

    /**
     * The piece is assigned to this Tile.
     */
    private Player player = null;

    /**
     * The Tiles row position on the board state.
     */
    private int row;

    /**
     * The Tiles column position on the board state.
     */
    private int col;

    /**
     * the Tiles strategic value which is the best possible move to make for given algorithms.
     */
    private int strategicValue;

    /**
     * Constructor of making an GameSession
     *
     * @param row            the row
     * @param col            the col
     * @param strategicValue the strategic value
     */
    public Tile(int row, int col, int strategicValue) {
        this.row = row;
        this.col = col;
        this.strategicValue = strategicValue;
    }

    /**
     * Instantiates a new Tile.
     *
     * @param row            the row
     * @param col            the col
     * @param strategicValue the strategic value
     * @param player         the player
     */
    public Tile(int row, int col, int strategicValue, Player player) {
        this(row, col, strategicValue);
        this.player = player;
    }

    /**
     * Gets player.
     *
     * @return Player, player of piece.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Sets player.
     *
     * @param player the player
     * @return Player, player of piece.
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Gets row.
     *
     * @return int, grid[x](row) position of tile.
     */
    public int getRow() {
        return row;
    }

    /**
     * Gets col.
     *
     * @return int, grid[y](column) position of tile.
     */
    public int getCol() {
        return col;
    }

    /**
     * Gets strategic value.
     *
     * @return int, strategicValue of the tile.
     */
    public int getStrategicValue() {
        return strategicValue;
    }

    /**
     * Sets strategic value.
     *
     * @param strategicValue the strategic value
     * @return int, strategicValue of the tile.
     */
    public void setStrategicValue(int strategicValue) {
        this.strategicValue = strategicValue;
    }
}
