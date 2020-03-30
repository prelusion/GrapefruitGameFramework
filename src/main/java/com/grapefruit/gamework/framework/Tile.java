package com.grapefruit.gamework.framework;

public class Tile {

    /**
     * The piece is assigned to this Tile.
     */
    private Piece piece;

    /**
     * The Tiles x position on the board state.
     */
    private int x;

    /**
     * The Tiles column position on the board state.
     */
    private int y;

    /**
     * the Tiles strategic value which is the best possible move to make for given algorithms.
     */
    private int strategicValue;


    /**
     * Constructor of making an GameSession
     * @param int x, gives the tile grid[x][] position from the board.
     * @param int y, gives the tile grid[][y] position from the board.
     * @param int strategicValue, is the given strategicValue of the Tile.
     */
    public Tile(int x, int y, int strategicValue) {
        this.x = x;
        this.y = y;
        this.strategicValue = strategicValue;
    }

    /**
     * @return Player, player of piece.
     */
    public Piece getPiece() {
        return piece;
    }
    /**
     * @return Player, player of piece.
     */
    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    /**
     * @return int, grid[x](x) position of tile.
     */
    public int getX() {
        return x;
    }

    /**
     * @return int, grid[y](column) position of tile.
     */
    public int getY() {
        return y;
    }
    /**
     * @return int, strategicValue of the tile.
     */
    public int getStrategicValue() {
        return strategicValue;
    }
}
