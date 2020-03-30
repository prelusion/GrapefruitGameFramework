package com.grapefruit.gamework.tictactoe;

import com.grapefruit.gamework.framework.GameCondition;
import com.grapefruit.gamework.framework.MoveSetter;
import com.grapefruit.gamework.framework.Rule;

public class TTTFactory {

    private TTTFactory() {}

    /**
     * This function creates a new game.
     * This happens by first making rules and conditions of the game.
     * Next making a MoveSetter which is a checker for the Rules and conditions.
     * And to return the new game being made.
     */
    public static TicTacToe create() {
        Rule rules = new TTTRules();
        GameCondition conditions = new TTTGameConditions();

        MoveSetter moveSetter = new TTTMoveSetter(rules, conditions);
        return new TicTacToe(moveSetter);
    }
}
