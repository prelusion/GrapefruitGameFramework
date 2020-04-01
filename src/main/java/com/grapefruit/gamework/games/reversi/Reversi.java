package com.grapefruit.gamework.games.reversi;

import com.grapefruit.gamework.framework.*;
import com.grapefruit.gamework.framework.player.Player;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class Reversi extends Game {


    public Reversi(Board board, Player playerWhite, Player playerBlack, int turnTimeout) {
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
    public void calculateGameResult() {
        if (!finished) {
            gameResult = GameResult.NONE;
        }
        gameResult = GameResult.TIE;
    }

    @Override
    public boolean playMove(int row, int col) {
        if(doMove(row, col)) {
            nextPlayer();
            return true;
        }
        return false;
    }

    @Override
    public boolean hasFinished() {
        return Arrays.stream(getPlayers())
                .noneMatch(player -> (getAvailableMoves(player).size()) > 0);
    }

    @Override
    public Player getWinner() {
        return null;
    }

    @Override
    public List<Tile> getAvailableMoves(Player player) {
        return getBoard().getAvailableMoves(player);
    }

}
