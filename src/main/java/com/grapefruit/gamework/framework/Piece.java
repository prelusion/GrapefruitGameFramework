package com.grapefruit.gamework.framework;

public class Piece {
    /**
     * The piece is assigned to this type.
     */
    private String type;
    /**
     * The piece is assigned to this player.
     */
    private Player player;

    /**
     * Constructor of making a piece
     * @param type, giving the piece a type.
     * @param type, giving the piece a player.
     */
    public Piece(String type, Player player) {
        this.type = type;
        this.player = player;
    }

    /**
     * @return Player, player of piece.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * @return type, Type of piece.
     */
    public String getType() {
        return type;
    }
}
