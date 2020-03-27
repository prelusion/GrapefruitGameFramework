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
     * Checks the result of the game and returns a String based on endstate or the winner.
     * Returns player name if player won.
     * Returns "remise" if its remise and an empty string if the game has not reached a end condition
     */
    public String getGameResult(GameSession session, Player[] players);
}
