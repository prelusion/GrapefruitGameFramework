package com.grapefruit.gamework.games.tictactoe;

import com.grapefruit.gamework.framework.old.Move;
import com.grapefruit.gamework.framework.old.Rule;
import com.grapefruit.gamework.framework.template.*;

public class TTTRules implements Rule {
    @Override
    public boolean validMove(Game session, Move move) {
        Board board = session.getBoard();
        Tile tile = move.getTile();
        if(board.hasPiece(tile)) {
            return false;
        }
        return true;
    }

}
