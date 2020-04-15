package com.grapefruit.gamework.framework;

import java.util.Map;

/**
 * The type Helpers.
 */
public class Helpers {

    /**
     * Gets winning player.
     *
     * @param board the board
     * @return the winning player
     */
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

    /**
     * Count pieces for player int.
     *
     * @param board  the board
     * @param player the player
     * @return the int
     */
    public static int countPiecesForPlayer(Board board, Player player) {
        Map<Player, Integer> pieces = board.countPieces();
        return pieces.get(player);
    }
}
