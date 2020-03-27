package com.grapefruit.gamework.tictactoe;

import com.grapefruit.gamework.framework.GameCondition;
import com.grapefruit.gamework.framework.MoveSetter;
import com.grapefruit.gamework.framework.Rule;

public class TicTacToeFactory {

    private TicTacToeFactory() {}

    public static TicTacToe create() {
        Rule tictactoeRules = new TicTacToeRules();
        GameCondition ticTacToeGameConditions = new TicTacToeGameConditions();

        MoveSetter tictactoeMoveSetter = new TicTacToeMoveSetter(tictactoeRules, ticTacToeGameConditions);
        return new TicTacToe(tictactoeMoveSetter);
    }
}
