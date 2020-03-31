package com.grapefruit.gamework.framework;

public class Player {

    private String name;
    private Colors color;
    private int score = 0;
    private int[] availableMoves;

    public Player(String name, Colors color) {
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
    public Colors getColor() {
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

    public String toString() {
        return color.toString();
    }
}
