package com.grapefruit.gamework.framework;

public class Computer extends Player {
    private MoveAlgorithm moveAlgorithm;
    public Computer(String name, Colors color, MoveAlgorithm moveAlgorithm) {
        super(name, color);
        this.moveAlgorithm = moveAlgorithm;
    }

    public Tile calculateMove(Board board) {
        return moveAlgorithm.calculateBestMove(board, this);
    }
}
