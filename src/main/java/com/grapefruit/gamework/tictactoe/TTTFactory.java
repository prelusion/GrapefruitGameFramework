package com.grapefruit.gamework.tictactoe;

import com.grapefruit.gamework.framework.GameCondition;
import com.grapefruit.gamework.framework.MoveSetter;
import com.grapefruit.gamework.framework.Rule;

public class TTTFactory {

    private TTTFactory() {}

    public static TicTacToe create() {
        Rule rules = new TTTRules();
        GameCondition conditions = new TTTGameConditions();

        MoveSetter moveSetter = new TTTMoveSetter(rules, conditions);
        return new TicTacToe(moveSetter);
    }
}
