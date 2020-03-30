package com.grapefruit.gamework.tictactoe;

import com.grapefruit.gamework.framework.*;

import java.util.HashSet;

public class TTTRules implements Rule {


    @Override
    /**
     * Check if the move is valid that is happening on the board
     */
    public boolean validMove(GameSession session, Move move) {
        Board board = session.getBoard();
        Tile tile = move.getTile();
        if(board.hasPiece(tile.getX(), tile.getY())) {
            return false;
        }
        return true;
    }

    @Override
    public HashSet<Tile> getValidMoves(Board board, Player player) {
        return null;
    }
}
