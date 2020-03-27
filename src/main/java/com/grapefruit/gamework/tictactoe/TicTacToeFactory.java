package com.grapefruit.gamework.tictactoe;

import com.grapefruit.gamework.framework.MoveSetter;
import com.grapefruit.gamework.framework.Rule;

public class TicTacToeFactory {

    private TicTacToeFactory() {}

    public static TicTacToe create() {
        Rule tictactoeRule1 = new TicTacToeRule1();
        Rule ticTacToeRule2 = new TicTacToeRule2();
        Rule[] rules = new Rule[] {tictactoeRule1, ticTacToeRule2};
        MoveSetter tictactoeMoveSetter = new TicTacToeMoveSetter(rules);
        return new TicTacToe(tictactoeMoveSetter);
    }
}
