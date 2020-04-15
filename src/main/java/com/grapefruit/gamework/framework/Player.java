package com.grapefruit.gamework.framework;

public class Player {

    private String name;
    private Colors color;
    private boolean isLocal = false;
    private boolean isAI = false;

    public Player(String name, Colors color) {
        this.name = name;
        this.color = color;
    }

    public Player(String name, Colors color, boolean isLocal) {
        this(name, color);
        this.isLocal = isLocal;
    }

    public Player(String name, Colors color, boolean isLocal, boolean isAI) {
        this(name, color, isLocal);
        this.isAI = isAI;
    }

    /**
     * Get name of player.
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Get color of player.
     *
     * @return color
     */
    public Colors getColor() {
        return color;
    }

    public String toString() {
        return color.toString();
    }

    public boolean isLocal() {
        return isLocal;
    }

    public boolean isAI() {
        return isAI;
    }
}
