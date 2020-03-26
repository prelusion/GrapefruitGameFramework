package com.grapefruit.gamework.framework;

public class HumanPlayer extends Player {

    private PlayerIO playerIO;

    public HumanPlayer(String name, String color, PlayerIO playerIO) {
        super(name, color);
        this.playerIO = playerIO;
    }

    @Override
    public void giveTurn() {
        setTurn(true);
        // setAvailableMoves(board.getAvailableMoves());
        int[] move = playerIO.askMove();
//        board.setMove(move);
        setTurn(false);
        // setAvailableMoves(null);
    }
}
