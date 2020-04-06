package com.grapefruit.gamework.games.tictactoe;

import com.grapefruit.gamework.framework.*;
import com.grapefruit.gamework.framework.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TicTacToe extends Game {

    public TicTacToe(Board board, Player[] players, int turnTimeout) {
        super(board, players, turnTimeout);
    }

    @Override
    public boolean isValidMove(int row, int col, Player player) {
        return !getBoard().hasPlayer(row, col);
    }

    @Override
    public boolean hasFinished() {
        return getBoard().isBoardFull() || Conditions.checkAllAdjacentConditions(getBoard());
    }

    @Override
    public Player getWinner() {
        Tile tile = Conditions.getTileOfAvailableConditions(getBoard());
        if (tile != null) {
            return tile.getPlayer();
        }
        return null;
    }

    @Override
    public void calculateScores() {
        Map<Player, Integer> scores = new HashMap<>();
        for (Player player: super.getPlayers()){
            scores.put(player, Helpers.countPiecesForPlayer(getBoard(), player));
        }
    }
}
