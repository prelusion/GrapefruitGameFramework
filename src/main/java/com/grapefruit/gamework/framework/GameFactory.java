package com.grapefruit.gamework.framework;

/**
 * The type Game factory.
 */
public abstract class GameFactory {

    /**
     * Create game.
     *
     * @param players the players
     * @return the game
     */
    public abstract Game create(Player[] players);

    /**
     * Create game.
     *
     * @param players    the players
     * @param difficulty the difficulty
     * @return the game
     */
    public abstract Game create(Player[] players, int difficulty);
}
