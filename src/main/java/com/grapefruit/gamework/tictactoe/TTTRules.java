package com.grapefruit.gamework.tictactoe;

import com.grapefruit.gamework.framework.*;

public class TTTRules implements Rule {
    @Override
    public boolean validMove(GameSession session, Move move) {
        Board board = session.getBoard();
        Tile tile = move.getTile();
        if(board.hasPiece(tile)) {
            return false;
        }
        return true;
    }
}
