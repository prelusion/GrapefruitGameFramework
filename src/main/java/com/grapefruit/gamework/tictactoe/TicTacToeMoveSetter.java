package com.grapefruit.gamework.tictactoe;

import com.grapefruit.gamework.framework.*;


public class TicTacToeMoveSetter extends MoveSetter {

    public TicTacToeMoveSetter(Rule rule, GameCondition condition) {
        super(rule, condition);
    }

    @Override
    public void setMove(GameSession session, Move move) {
        if (isValidMove(session, move)) {
            session.setMove(move);
        }
    }
}
