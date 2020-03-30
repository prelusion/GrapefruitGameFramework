package com.grapefruit.gamework.tictactoe;

import com.grapefruit.gamework.framework.*;


public class TTTMoveSetter extends MoveSetter {

    /**
     * The MoveSetter is a checker implementation for games.
     * @param rule, Rules made for the TTT.
     * @param condition, The GameConditions made for TTT.
     */
    public TTTMoveSetter(Rule rule, GameCondition condition) {
        super(rule, condition);
    }

    @Override
    /**
     * Check if the move is valid for the game
     * @param session, Gamesession.
     * @param move, The move that is being made.
     */
    public void setMove(GameSession session, Move move) {
        if (isValidMove(session, move)) {
            session.setMove(move);
        }
    }
}
