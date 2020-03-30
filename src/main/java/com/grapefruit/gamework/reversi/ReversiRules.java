package com.grapefruit.gamework.reversi;

import com.grapefruit.gamework.framework.*;

import java.util.HashSet;

public class ReversiRules implements Rule {
    @Override
    public boolean validMove(GameSession session, Move move) {
        return false;
    }

    @Override
    public HashSet<Tile> getValidMoves(Board board, Player player) {
        HashSet<Tile> playerOwnedTiles = new HashSet<>();
        HashSet<Tile> validMoves = new HashSet<>();
        for (Tile[] column: board.getGrid()){
            for (Tile tile: column){
                if (tile.getPiece() != null){
                    if (tile.getPiece().getPlayer() == player){
                        playerOwnedTiles.add(tile);
                    }
                }
            }
        }

        for (Tile tile: playerOwnedTiles) {
            HashSet<Tile> neighbours = getDirectNeighbours(board, tile);
            for (Tile t : neighbours) {
                if (t.getPiece() != null) {
                    if (t.getPiece().getPlayer() != player) {
                        getEmptyTileInDirection(board, player, t.getX(), t.getY(), tile.getX() - t.getX(), tile.getY() - t.getY());
                    }
                }
            }
        }

        return validMoves;
    }


    private HashSet<Tile> getDirectNeighbours(Board board, Tile tile){
        HashSet<Tile> neighbours = new HashSet<>();
        return neighbours;
    }

    private Tile getEmptyTileInDirection(Board board,Player player, int startx, int starty, int dx, int dy) {
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
