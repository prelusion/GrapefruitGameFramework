package com.grapefruit.gamework.framework;

public interface GameCondition {
    /**
     * Checks if the game should be ended.
     * @param session, is a GameSession which is here to get info about the game and board.
     * @return boolean, for if the game.
     */
    public boolean checkEndConditions(GameSession session);

    /**
     * Checks which player won the match and returns that player.
     */
    public Player getWinner(GameSession session, Player players);

    /**
     * Checks the result and should return the player that won. Return null when no players won.
     */
    public String getGameResult(GameSession session, Player[] players);
}
