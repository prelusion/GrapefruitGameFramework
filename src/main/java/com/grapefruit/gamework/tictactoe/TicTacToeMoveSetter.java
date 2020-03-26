package com.grapefruit.gamework.tictactoe;

import com.grapefruit.gamework.framework.Board;
import com.grapefruit.gamework.framework.Move;
import com.grapefruit.gamework.framework.MoveSetter;
import com.grapefruit.gamework.framework.Rule;


public class TicTacToeMoveSetter extends MoveSetter {

    public TicTacToeMoveSetter(Rule[] rules) {
        super(rules);
    }

    @Override
    public void setMove(Board board, Move move) {
        if (isValidMove(board, move)) {
            board.setMove(move);
        }
    }
}
