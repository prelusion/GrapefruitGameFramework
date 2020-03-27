package com.grapefruit.gamework.framework;

public class OfflineGameSession extends GameSession {
    public OfflineGameSession(MoveSetter moveSetter, Player[] players, int turnTimeout, int boardSize) {
        super(moveSetter, players, turnTimeout, boardSize);
    }

    @Override
    public void start() {

    }

    /**
     * @param Move, move is given to set the move on the board and apply all necessary changes.
     */
    public void setMove(Move move) {

    }
}
