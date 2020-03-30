package com.grapefruit.gamework.games.reversi;

import com.grapefruit.gamework.app.resources.ImageRegistry;
import com.grapefruit.gamework.framework.*;
import javafx.scene.image.Image;

import java.util.HashSet;
import java.util.List;

public class Reversi extends Game {

    public static Image IMAGE_BLACK_PIECE = ImageRegistry.GAME_PIECE_BLACK;
    public static Image IMAGE_WHITE_PIECE = ImageRegistry.GAME_PIECE_WHITE;

    public Reversi(Board board, Player[] players, int turnTimeout) {
        super(board, players, turnTimeout);
        board.setPiece(3, 3, players[0]);
        board.setPiece(4, 4, players[1]);
        board.setPiece(3, 4, players[0]);
        board.setPiece(4, 3, players[1]);

    }

    @Override
    public boolean isValidMove(int row, int col, Player player) {
        return false;
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
    public List<Tile> getAvailableMoves(Player player) {
        /* TODO grid of board should be private, and game should manipulate board through methods.
        *   If the method requires special methods to operate on the grid, please inherit the board itself
        *   and create methods on the board for manipulation.
        * */
//        HashSet<Tile> playerOwnedTiles = new HashSet<>();
//        HashSet<Tile> validMoves = new HashSet<>();
//        for (Tile[] column: board.getGrid()){
//            for (Tile tile: column){
//                if (tile.getPlayer() != null){
//                    if (tile.getPlayer() == player){
//                        playerOwnedTiles.add(tile);
//                    }
//                }
//            }
//        }
//
//        for (Tile tile: playerOwnedTiles) {
//            HashSet<Tile> neighbours = getDirectNeighbours(board, tile);
//            for (Tile t : neighbours) {
//                if (t.getPlayer() != null) {
//                    if (t.getPlayer() != player) {
//                        getEmptyTileInDirection(board, player, t.getCol(), t.getRow(), tile.getCol() - t.getCol(), tile.getRow() - t.getRow());
//                    }
//                }
//            }
//        }
//
//        return validMoves;

        return null;
    }

    private HashSet<Tile> getDirectNeighbours(Board board, Tile tile){
        return new HashSet<>();
    }

    private Tile getEmptyTileInDirection(Board board,Player player, int startx, int starty, int dx, int dy) {
        /* TODO grid of board should be private, and game should manipulate board through methods.
         *   If the method requires special methods to operate on the grid, please inherit the board itself
         *   and create methods on the board for manipulation.
         * */
//        if (board.isValidLocation(startx + dx, starty + dy)) {
//            if (board.hasPiece(startx + dx, starty + dy)) {
//                if (board.getGrid()[startx + dx][starty + dy].getPlayer() != player) {
//                    return getEmptyTileInDirection(board, player, startx + dy, starty + dy, dx, dy);
//                }
//            }
//        }
        return null;
    }
}
