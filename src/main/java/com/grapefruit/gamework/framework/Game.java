package com.grapefruit.gamework.framework;

import com.grapefruit.gamework.framework.Board;
import com.grapefruit.gamework.framework.Move;
import com.grapefruit.gamework.framework.Rule;

public abstract class Game {
    public Game() {

    }

    public GameSession createSession(MoveSetter moveSetter, Player[] players) {
        return new OfflineGameSession(moveSetter, players, 10, 9);
    }
}
