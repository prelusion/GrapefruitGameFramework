package com.grapefruit.gamework.framework;

public class HumanPlayer extends Player {

    private PlayerIO playerIO;

    public HumanPlayer(String name, String color, PlayerIO playerIO) {
        super(name, color);
        this.playerIO = playerIO;
    }

    @Override
    public void giveTurn(GameSession session, int timeout) {
        setTurn(true);
        // setAvailableMoves(board.getAvailableMoves());
        int[] move = playerIO.askMove();
//        board.setMove(move);
        setTurn(false);
        // setAvailableMoves(null);
        if (session.isValidMove(null, null)) {
            session.setMove(null);
        }
    }
}
