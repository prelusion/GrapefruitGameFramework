package com.grapefruit.gamework.tictactoe;

import com.grapefruit.gamework.framework.*;


public class TTTMoveSetter extends MoveSetter {

    public TTTMoveSetter(Rule rule, GameCondition condition) {
        super(rule, condition);
    }

    @Override
    public void setMove(GameSession session, Move move) {
        if (isValidMove(session, move)) {
            session.setMove(move);
        }
    }
}
