package com.grapefruit.gamework.framework;

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
     * Can check one to make a condition change its effect
     */
    private boolean checked = false;


    /**
     * Constructor of making an GameSession
     *
     * @param row, gives the tile grid[x][] position from the board.
     * @param col, gives the tile grid[][y] position from the board.
     * @param strategicValue, is the given strategicValue of the Tile.
     */
    public Tile(int row, int col, int strategicValue) {
        this.row = row;
        this.col = col;
        this.strategicValue = strategicValue;
    }

    public Tile(int row, int col, int strategicValue, Player player) {
        this(row, col, strategicValue);
        this.player = player;
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

    /**
     * @return int, strategicValue of the tile.
     */
    public void setStrategicValue(int strategicValue) {
        this.strategicValue = strategicValue;
    }



    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
