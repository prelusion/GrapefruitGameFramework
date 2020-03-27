package com.grapefruit.gamework.framework.template;

public abstract class Player {

    private String name;
    private String color;
    private int score = 0;
    private boolean hasTurn = false;
    private int[] availableMoves;

    public Player(String name, String color) {
        this.name = name;
        this.color = color;
    }

    /**
     * Get name of player.
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Get color of player.
     * @return color
     */
    public String getColor() {
        return color;
    }

    /**
     * Get current score of player.
     * @return score
     */
    public int getScore() {
        return score;
    }

    /**
     * Give player turn to set a move.
     */
    public abstract void giveTurn(GameSession session, int timeout);

    /**
     * Check if player has the current turn.
     * @return boolean of current turn
     */
    public boolean hasTurn() {
        return hasTurn;
    }

    protected void setTurn(boolean turn) {
        hasTurn = turn;
    }
    /**
     * Check if player has any moves left.
     * @return boolean whether player has moves left
     */
    public boolean hasMoves() {
        return false;
    }

    /**
     * Get available moves for player.
     * @return list of available moves
     */
    public int[] getAvailableMoves() {
        return availableMoves;
    }

    /**
     * Set available moves for player.
     * @param availableMoves
     */
    public void setAvailableMoves(int[] availableMoves) {
        this.availableMoves = availableMoves;
    }
}
