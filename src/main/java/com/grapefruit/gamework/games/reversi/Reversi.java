package com.grapefruit.gamework.games.reversi;

import com.grapefruit.gamework.framework.*;

import java.util.HashSet;

public class Reversi extends Game {


    public Reversi(Board board, Player playerWhite, Player playerBlack, int turnTimeout) {
        super(board, new Player[]{playerBlack, playerWhite}, turnTimeout);
        board.setPiece(3, 3, playerWhite);
        board.setPiece(4, 4, playerWhite);
        board.setPiece(3, 4, playerBlack);
        board.setPiece(4, 3, playerBlack);

    }

    @Override
    public boolean isValidMove(int row, int col, Player player) {
        HashSet<Tile> validMoves = getBoard().getAvailableMoves(player);
        for (Tile tile : validMoves) {
            if (tile.getRow() == row && tile.getCol() == col) return true;
        }
        return false;
    }

    @Override
    public void calculateGameResult() {

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
        return getBoard().getAvailableMoves(player);
    }
}
