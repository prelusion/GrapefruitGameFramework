package com.grapefruit.gamework.framework;

public class Piece {
    /**
     * The piece is assigned to this player.
     */
    private Player player;

    /**
     * Constructor of making a piece
     * @param Player, giving the piece a player.
     */
    public Piece(Player player) {
        this.player = player;
    }

    /**
     * @return Player, player of piece.
     */
    public Player getPlayer() {
        return player;
    }
}
