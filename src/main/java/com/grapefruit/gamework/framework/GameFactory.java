package com.grapefruit.gamework.framework;

/**
 * The type Game factory.
 */
public abstract class GameFactory {


    /**
     * Instantiates a new board and create a game object.
     *
     * @param players
     * @return game
     */
    public abstract Game create(Player[] players);

    /**
     * Instantiates a new board and create a game object.
     *
     * @param players
     * @param difficulty
     * @return game
     */
    public abstract Game create(Player[] players, int difficulty);
}
