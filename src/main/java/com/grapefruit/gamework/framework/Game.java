package com.grapefruit.gamework.framework;

import com.grapefruit.gamework.framework.Board;
import com.grapefruit.gamework.framework.Move;
import com.grapefruit.gamework.framework.Rule;

public abstract class Game {
    /**
     * The moveSetter is a variable Object that saves the rules and conditions of the game.
     */
    private MoveSetter moveSetter;

    /**
     * @param moveSetter sets the game moveSetter within the global variable.
     */
    public Game(MoveSetter moveSetter) {
        this.moveSetter = moveSetter;
    }

    /**
     * @param players gives the players to the gameSession.
     * @return gameSession with the moveSetter, players, turnTimeout and boardSize.
     */
    public GameSession createSession(Player[] players) {
        return new OfflineGameSession(moveSetter, players, 10, 9);
    }
}
