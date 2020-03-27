package com.grapefruit.gamework.framework.template;

public abstract class Game {

    private MoveSetter moveSetter;

    public Game(MoveSetter moveSetter) {
        this.moveSetter = moveSetter;
    }

    public GameSession createSession(Player[] players) {
        return new OfflineGameSession(moveSetter, players, 10, 9);
    }
}
