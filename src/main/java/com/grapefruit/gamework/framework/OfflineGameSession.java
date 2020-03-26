package com.grapefruit.gamework.framework;

public class OfflineGameSession extends GameSession {
    public OfflineGameSession(int turnTimeout, int boardSize) {
        super(turnTimeout, boardSize);
        start();
    }

    @Override
    public void start() {

    }
}
