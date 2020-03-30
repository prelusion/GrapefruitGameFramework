package com.grapefruit.gamework.games.reversi;

import com.grapefruit.gamework.app.resources.ImageRegistry;
import com.grapefruit.gamework.framework.*;
import javafx.scene.image.Image;

import java.util.HashSet;
import java.util.List;

public class Reversi extends Game {


    public Reversi(Board board, Player[] players, int turnTimeout) {
        super(board, players, turnTimeout);
        board.setPiece(3, 3, players[1]);
        board.setPiece(4, 4, players[1]);
        board.setPiece(3, 4, players[0]);
        board.setPiece(4, 3, players[0]);

    }

    @Override
    public boolean isValidMove(int row, int col, Player player) {
        return false;
    }

    @Override
    public GameResult checkGameResult() {
        return null;
    }

    @Override
    public boolean hasGameFinished() {
        return false;
    }

    @Override
    public boolean hasWinner() {
        return false;
    }

    @Override
    public boolean isTie() {
        return false;
    }

    @Override
    public Player getWinner() {
        return null;
    }

    @Override
    public HashSet<Tile> getAvailableMoves(Player player) {
        HashSet<Tile> playerOwnedTiles = new HashSet<>();
        HashSet<Tile> validMoves = new HashSet<>();
        for (Tile[] column: super.getBoard().getGrid()){
            for (Tile tile: column){
                if (tile.getPlayer() != null){
                    if (tile.getPlayer() == player){
                        playerOwnedTiles.add(tile);
                    }
                }
            }
        }

        for (Tile tile: playerOwnedTiles) {
            HashSet<Tile> neighbours = getDirectNeighbours(super.getBoard(), tile);
            for (Tile t : neighbours) {
                if (t.getPlayer() != null) {
                    if (t.getPlayer() != player) {
                        Tile emptyTile = getEmptyTileInDirection(super.getBoard(), player, t.getCol(), t.getRow(), t.getCol() - tile.getCol(), t.getRow() - tile.getRow());
                        if (emptyTile != null) {
                            if (!validMoves.contains(emptyTile)) {
                                validMoves.add(emptyTile);
                            }
                        }
                   }
               }
            }
        }

        return validMoves;
    }

    private HashSet<Tile> getDirectNeighbours(Board board, Tile tile){
        HashSet<Tile> neighbours = new HashSet<>();
        for (int[] coord: Util.outerGrid){
            if (board.isValidLocation(tile.getRow() + coord[0], tile.getCol() + coord[1])){
                neighbours.add(board.getGrid()[tile.getRow() + coord[0]][tile.getCol() + coord[1]]);
            }
        }
        return neighbours;
    }

    private Tile getEmptyTileInDirection(Board board,Player player, int startx, int starty, int dx, int dy) {
        if (board.isValidLocation(startx + dx, starty + dy)) {
            if (board.hasPlayer(startx + dx, starty + dy)) {
               if (board.getGrid()[startx + dx][starty + dy].getPlayer() != player) {
                    return getEmptyTileInDirection(board, player, startx + dx, starty + dy, dx, dy);
                }
            } else {
                return board.getGrid()[startx + dx][ starty + dy];
            }
        }
        return null;
    }
}
