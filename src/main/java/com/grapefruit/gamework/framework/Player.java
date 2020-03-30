package com.grapefruit.gamework.framework;

public class Player {

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

    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Get current score of player.
     * @return score
     */
    public int getScore() {
        return score;
    }

    protected void setTurn(boolean turn) {
        hasTurn = turn;
    }

    /**
     * Check if player has the current turn.
     * @return boolean of current turn
     */
    public boolean hasTurn() {
        return hasTurn;
    }

    public String toString() {
        return this.color;
    }
}
