package com.grapefruit.gamework.reversi;

import com.grapefruit.gamework.framework.GameSession;
import com.grapefruit.gamework.framework.MoveSetter;
import com.grapefruit.gamework.framework.Player;

public class ReversiGameSession extends GameSession {

    /**
     * Constructor of making an GameSession
     *
     * @param moveSetter
     * @param players
     * @param turnTimeout
     * @param boardSize
     */
    public ReversiGameSession(MoveSetter moveSetter, Player[] players, int turnTimeout, int boardSize) {
        super(moveSetter, players, turnTimeout, boardSize);
    }

    @Override
    public void start() {

    }

}
