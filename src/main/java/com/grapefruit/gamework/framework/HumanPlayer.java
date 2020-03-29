package com.grapefruit.gamework.framework;

public class HumanPlayer extends Player {

    public HumanPlayer(String name, Team.TeamColour colour) {
        super(name, colour);
    }


    @Override
    public void giveTurn(GameSession session, int timeout) {}
}
