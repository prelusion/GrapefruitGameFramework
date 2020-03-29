package com.grapefruit.gamework.reversi;

import com.grapefruit.gamework.framework.*;

public class ReversiMoveSetter extends MoveSetter {

    public ReversiMoveSetter() {
        super(new ReversiRules(), new ReversiConditions());
    }

    @Override
    public void setMove(GameSession session, Move move) {

    }
}
