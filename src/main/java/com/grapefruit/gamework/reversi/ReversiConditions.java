package com.grapefruit.gamework.reversi;

import com.grapefruit.gamework.framework.*;

public class ReversiConditions implements GameCondition {

    @Override
    public boolean checkEndConditions(GameSession session) {
        return false;
    }

    @Override
    public Result isWinner(GameSession session, Player[] players) {
        return null;
    }

    @Override
    public Player getWinner(GameSession session, Player[] players) {
        return null;
    }

    @Override
    public GameState getGameState() {
        return null;
    }
}
