package com.grapefruit.gamework.framework;

/**
 * The type Player.
 */
public class Player {

    private String name;
    private Colors color;
    private boolean isLocal = false;
    private boolean isAI = false;

    /**
     * Instantiates a new Player.
     *
     * @param name  the name
     * @param color the color
     */
    public Player(String name, Colors color) {
        this.name = name;
        this.color = color;
    }

    /**
     * Instantiates a new Player.
     *
     * @param name    the name
     * @param color   the color
     * @param isLocal the is local
     */
    public Player(String name, Colors color, boolean isLocal) {
        this(name, color);
        this.isLocal = isLocal;
    }

    /**
     * Instantiates a new Player.
     *
     * @param name    the name
     * @param color   the color
     * @param isLocal the is local
     * @param isAI    the is ai
     */
    public Player(String name, Colors color, boolean isLocal, boolean isAI) {
        this(name, color, isLocal);
        this.isAI = isAI;
    }

    /**
     * Get name of player.
     *
     * @return name name
     */
    public String getName() {
        return name;
    }

    /**
     * Get color of player.
     *
     * @return color color
     */
    public Colors getColor() {
        return color;
    }

    public String toString() {
        return color.toString();
    }

    /**
     * Is local boolean.
     *
     * @return the boolean
     */
    public boolean isLocal() {
        return isLocal;
    }

    /**
     * Is ai boolean.
     *
     * @return the boolean
     */
    public boolean isAI() {
        return isAI;
    }
}
