package com.grapefruit.gamework.reversi;

import com.grapefruit.gamework.framework.GameSession;
import com.grapefruit.gamework.framework.Player;
import com.grapefruit.gamework.framework.Team;

public class ReversiPlayer extends Player {

    public ReversiPlayer(String name, Team.TeamColour color) {
        super(name, color);
    }

    @Override
    public void giveTurn(GameSession session, int timeout) {

    }
}
