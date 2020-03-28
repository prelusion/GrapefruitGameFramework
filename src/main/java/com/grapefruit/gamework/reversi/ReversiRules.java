package com.grapefruit.gamework.reversi;

import com.grapefruit.gamework.framework.*;

import java.util.HashSet;

public class ReversiRules implements Rule {
    @Override
    public boolean validMove(GameSession session, Move move) {
        return false;
    }

    @Override
    public HashSet<Tile> getValidMoves(Player player) {
        return null;
    }


    private HashSet<Tile> getDirectNeighbours(Board board, Tile tile){
        HashSet<Tile> neighbours = new HashSet<>();

        return neighbours;
    }

    private Tile getEmptyTileInDirection(Board board,Player player,  int startx, int starty, int dx, int dy) {
        if (board.isValidLocation(startx + dx, starty + dy)) {
            if (board.hasPiece(startx + dx, starty + dy)) {
                if (board.getGrid()[startx + dx][starty + dy].getPiece().getPlayer() != player) {
                    return getEmptyTileInDirection(board, player, startx + dy, starty + dy, dx, dy);
                }
            }
        }
        return null;
    }
}
