package com.grapefruit.gamework.tictactoe;

import com.grapefruit.gamework.framework.Board;
import com.grapefruit.gamework.framework.Move;
import com.grapefruit.gamework.framework.Rule;
import com.grapefruit.gamework.framework.Tile;

public class TicTacToeRule1 implements Rule {

    @Override
    public boolean validMove(Board board, Move move) {
        // check drie op rij
        return false;
    }
}
