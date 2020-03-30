package com.grapefruit.gamework.framework.old;

import com.grapefruit.gamework.framework.template.Game;
import com.grapefruit.gamework.framework.template.Player;

public interface GameCondition {
    /**
     * Checks if the game should be ended.
     * @param session, is a GameSession which is here to get info about the game and board.
     * @return boolean, for if the game.
     */
    public boolean checkEndConditions(Game session);

    /**
     * Return Result, Returns WINNING, TIE
     */
    public Result isWinner(Game session, Player[] players);


    /**
     * Checks which player won the match and returns that player.
     */
    public Player getWinner(Game session, Player[] players);


    /**
     * Returns GameState, Returns RUNNING, ENDED
     */
    public GameState getGameState();
}
