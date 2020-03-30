package com.grapefruit.gamework.framework;

public abstract class MoveSetter {
    /**
     * The rules for the MoveSetter
     */
    private Rule rules;

    /**
     * The gameConditions of the MoveSetter.
     */
    private GameCondition gameConditions;

    /**
     * Constructor MoveSetter
     * @param rules
     * @param conditions
     */
    public MoveSetter(Rule rules, GameCondition conditions) {
        this.rules = rules;
        this.gameConditions = conditions;
    }

    /**
     * Checks whether move that has been made is valid
     * @param session
     * @param move
     * @return boolean
     */
    public boolean isValidMove(GameSession session, Move move) {
        if (!rules.validMove(session, move)) return false;
        return true;
    }

    /**
     * Checks whether the game has ended
     * @param session
     * @return boolean
     */
    public boolean isGameEnded(GameSession session) {
        if (!gameConditions.checkEndConditions(session)) return false;
        return true;
    }

    /**
     * Returns String of player name if a player has won. returns "Remise" if no player has won but the game has ended, returns null if the game hasnt ended.
     * Uses getWinner() to check who won.
     * @param session
     * @param players
     * @return GameState
     */
    public GameState getGameState(GameSession session, Player[] players) {
        return gameConditions.getGameState();
    }

    /**
     * Checks if a player won the match.
     * @param session
     * @param players
     * @return Result of the game.
     */
    public Result isWinner(GameSession session, Player[] players) {
        if (gameConditions.isWinner(session, players) == null) {

        }
        return null;
    }

    /**
     * Checks which player won the match and returns that player.
     * @param session
     * @param players
     * @return Player, the player that won the match
     */
    public Player getWinner(GameSession session, Player[] players) {
        if (gameConditions.getWinner(session, players) == null) {

        }
        return null;
    }

    public Rule getRules() {
        return rules;
    }

    public abstract void setMove(GameSession session, Move move);
}
