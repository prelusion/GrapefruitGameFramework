package com.grapefruit.gamework.games.tttnew;

import com.grapefruit.gamework.framework.template.Board;
import com.grapefruit.gamework.framework.template.Game;
import com.grapefruit.gamework.framework.template.Player;
import com.grapefruit.gamework.framework.template.Tile;

import java.util.List;

public class TTTGame extends Game {

    /**
     * Constructor of making an GameSession
     *
     * @param board
     * @param players
     * @param turnTimeout
     */
    public TTTGame(Board board, Player[] players, int turnTimeout) {
        super(board, players, turnTimeout);
    }

    @Override
    public boolean isValidMove(Player player, Tile tile) {
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
        return null;
    }
}
