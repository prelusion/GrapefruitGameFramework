package com.grapefruit.gamework.framework.template;

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
     * @param int row, gives the tile grid[x][] position from the board.
     * @param int col, gives the tile grid[][y] position from the board.
     * @param int strategicValue, is the given strategicValue of the Tile.
     */
    public Tile(int row, int col, int strategicValue) {
        this.row = row;
        this.col = col;
        this.strategicValue = strategicValue;
    }

    /**
     * @return Player, player of piece.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * @return Player, player of piece.
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * @return int, grid[x](row) position of tile.
     */
    public int getRow() {
        return row;
    }

    /**
     * @return int, grid[y](column) position of tile.
     */
    public int getCol() {
        return col;
    }

    /**
     * @return int, strategicValue of the tile.
     */
    public int getStrategicValue() {
        return strategicValue;
    }
}
