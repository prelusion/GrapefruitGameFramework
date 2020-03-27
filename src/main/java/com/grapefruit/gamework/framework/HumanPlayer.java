package com.grapefruit.gamework.framework;

public class HumanPlayer extends Player {

    public HumanPlayer(String name, String color) {
        super(name, color);
    }

    @Override
    public void giveTurn(Board board, int timeout) {}
}
