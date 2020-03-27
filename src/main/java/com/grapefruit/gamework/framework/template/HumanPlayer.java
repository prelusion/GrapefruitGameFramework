package com.grapefruit.gamework.framework.template;

public class HumanPlayer extends Player {

    public HumanPlayer(String name, String color) {
        super(name, color);
    }


    @Override
    public void giveTurn(GameSession session, int timeout) {}
}
