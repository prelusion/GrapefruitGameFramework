package com.grapefruit.gamework.framework;

import com.grapefruit.gamework.framework.Board;
import com.grapefruit.gamework.framework.Move;
import com.grapefruit.gamework.framework.Rule;

public abstract class Game {

    private MoveSetter moveSetter;

    public Game(MoveSetter moveSetter) {
        this.moveSetter = moveSetter;
    }

    public GameSession createSession(Player[] players) {
        return new OfflineGameSession(moveSetter, players, 10, 9);
    }
}
