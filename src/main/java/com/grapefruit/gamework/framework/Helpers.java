package com.grapefruit.gamework.framework;

import java.util.Map;

public class Helpers {
    public static Player getWinningPlayer(Board board) {
        Map<Player, Integer> pieces = board.countPieces();

        int winnerPieces = 0;
        Player winner = null;

        for (Map.Entry<Player, Integer> entry : pieces.entrySet()) {
            if (entry.getValue() > winnerPieces) {
                winnerPieces = entry.getValue();
                winner = entry.getKey();
            } else if (entry.getValue() == winnerPieces) {
                winner = null;
            }
        }

        return winner;
    }
}
