package com.grapefruit.gamework.framework;

public class HumanPlayer extends Player {

    /**
     * @param name The name of the player.
     * @param color The color of the player.
     */
    public HumanPlayer(String name, String color) {
        super(name, color);
    }


    @Override
    /**
     * @param session Is the gameSession being used for the game.
     * @param timeout Timeout is the amount of time given for a turn.
     */
    public void giveTurn(GameSession session, int timeout) {}
}
