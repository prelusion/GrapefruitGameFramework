package com.grapefruit.gamework.framework.player;

import com.grapefruit.gamework.framework.Colors;

public class Player {

    private String name;
    private Colors color;
    private int score = 0;
    private boolean localPlayer;

    public Player(String name, Colors color, boolean localPlayer) {
        this.name = name;
        this.color = color;
        this.localPlayer = localPlayer;
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
