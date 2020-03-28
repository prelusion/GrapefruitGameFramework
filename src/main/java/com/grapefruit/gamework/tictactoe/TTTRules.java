package com.grapefruit.gamework.tictactoe;

import com.grapefruit.gamework.framework.*;

import java.util.HashSet;

public class TTTRules implements Rule {


    @Override
    public boolean validMove(GameSession session, Move move) {
        Board board = session.getBoard();
        Tile tile = move.getTile();
        if(board.hasPiece(tile.getX(), tile.getY())) {
            return false;
        }
        return true;
    }

    @Override
    public HashSet<Tile> getValidMoves(Player player) {
        return null;
    }

}
