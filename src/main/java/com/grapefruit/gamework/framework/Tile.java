package com.grapefruit.gamework.framework;

public class Tile {

    /**
     * The piece is assigned to this Tile.
     */
    private Piece piece;

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
     * @param row, gives the tile grid[x][] position from the board.
     * @param col, gives the tile grid[][y] position from the board.
     * @param strategicValue, is the given strategicValue of the Tile.
     */
    public Tile(int row, int col, int strategicValue) {
        this.row = row;
        this.col = col;
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
        return row;
    }

    /**
     * @return int, grid[y](column) position of tile.
     */
    public int getY() {
        return col;
    }
    /**
     * @return int, strategicValue of the tile.
     */
    public int getStrategicValue() {
        return strategicValue;
    }
}
