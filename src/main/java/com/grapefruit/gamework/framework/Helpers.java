package com.grapefruit.gamework.framework;

import java.util.Map;

/**
 * The type Helpers.
 */
public class Helpers {

    /**
     * Gets the winning player if the game has one.
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
     * Count the amount of pieces the player has on the board.
     *
     * @param board  the board
     * @param player the player
     * @return the amount of pieces int
     */
    public static int countPiecesForPlayer(Board board, Player player) {
        Map<Player, Integer> pieces = board.countPieces();
        return pieces.get(player);
    }
}
