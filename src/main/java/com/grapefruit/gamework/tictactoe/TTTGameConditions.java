package com.grapefruit.gamework.tictactoe;

import com.grapefruit.gamework.framework.Board;
import com.grapefruit.gamework.framework.GameCondition;
import com.grapefruit.gamework.framework.GameSession;
import com.grapefruit.gamework.framework.Player;

public class TTTGameConditions implements GameCondition {
    boolean endReached;

    @Override
    /**
     * Returns true if there is an end condition reached.
     * @return boolean
     */
    public boolean checkEndConditions(GameSession session) {
        Board board = session.getBoard();
        if(board.isBoardFull()) {
            endReached = true;
            return true;
        }
        return false;
    }

    /**
     * Returns String of player name if a player has won. returns "Remise" if no player has won but the game has ended, returns null if the game hasnt ended.
     * Uses getWinner() to check who won.
     * @return boolean
     */
    @Override
    public String getGameResult(GameSession session, Player[] players) {
        if(endReached) {
            for(Player player : players) {
                if(getWinner(session, player) != null) {
                    return player.getName();
                }
            }
            return "remise";
        }
        return "";
    }


    /**
     * Checks which player won the match and returns that player.
     */
    @Override
    public Player getWinner(GameSession session, Player player) {
        return null;
    }
}
