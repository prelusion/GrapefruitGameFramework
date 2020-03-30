package com.grapefruit.gamework.framework;

import java.util.HashSet;

public interface Rule {
    /**
     * Checks if the following move will be valid or not.
     * @param session, is a GameSession which is here to get info about the game and board.
     * @param move, is a Move to check whether the move is valid.
     * @return boolean, for if the move is valid or not.
     */
    public boolean validMove(GameSession session, Move move);

    public HashSet<Tile> getValidMoves(Board board, Player player);
}
