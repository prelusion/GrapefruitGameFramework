package com.grapefruit.gamework.games.tictactoe;

import com.grapefruit.gamework.framework.template.GameCondition;
import com.grapefruit.gamework.framework.template.MoveSetter;
import com.grapefruit.gamework.framework.template.Rule;

public class TTTFactory {

    private TTTFactory() {}

    public static GameTicTacToe create() {
        Rule rules = new TTTRules();
        GameCondition conditions = new TTTGameConditions();

        MoveSetter moveSetter = new TTTMoveSetter(rules, conditions);
        return new GameTicTacToe(moveSetter);
    }
}
