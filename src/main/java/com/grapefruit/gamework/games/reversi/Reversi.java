package com.grapefruit.gamework.games.reversi;

import com.grapefruit.gamework.framework.*;

import java.util.HashMap;
import java.util.List;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.util.Map;
public class Reversi extends Game {

    public Reversi(Board board, Player playerBlack, Player playerWhite, int turnTimeout) {
        super(board, new Player[]{playerBlack, playerWhite}, turnTimeout);
        board.setPlayer(3, 3, playerWhite);
        board.setPlayer(4, 4, playerWhite);
        board.setPlayer(3, 4, playerBlack);
        board.setPlayer(4, 3, playerBlack);
    }

    @Override
    public boolean isValidMove(int row, int col, Player player) {
        List<Tile> validMoves = getBoard().getAvailableMoves(player);

        for (Tile tile : validMoves) {
            if (tile.getRow() == row && tile.getCol() == col) return true;
        }

        return false;
    }

    @Override
    public boolean hasFinished() {
        return getBoard().anyMovesLeft(getPlayers());
    }

    @Override
    public Player getWinner() {
        return Helpers.getWinningPlayer(getBoard());
    }
}
