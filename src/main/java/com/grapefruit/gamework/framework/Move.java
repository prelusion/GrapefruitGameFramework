package com.grapefruit.gamework.framework;

public class Move {
    /**
     * The tile for the given move.
     */
    private Tile tile;

    /**
     * The player for the given move.
     */
    private Player player;

    /**
     * Constructor of making an Move object
     * @param Tile tile, gives a reference to the given tile.
     * @param Player player, gives a reference to the given player.
     */
    public Move(Tile tile, Player player) {
        this.tile = tile;
        this.player = player;
    }

    /**
     * @return Tile, tile of given move.
     */
    public Tile getTile() {
        return tile;
    }

    /**
     * @return Player, player of given move.
     */
    public Player getPlayer() {
        return player;
    }

}
