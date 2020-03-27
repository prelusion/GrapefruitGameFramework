package com.grapefruit.gamework.framework.template;

public abstract class MoveSetter {
    private Rule rules;
    private GameCondition gameConditions;

    public MoveSetter(Rule rules, GameCondition conditions) {
        this.rules = rules;
        this.gameConditions = conditions;
    }

    public boolean isValidMove(GameSession session, Move move) {
        if (!rules.validMove(session, move)) return false;
        return true;
    }

    public boolean isGameEnded(GameSession session) {
        if (!gameConditions.checkEndConditions(session)) return false;
        return true;
    }

    public GameState getGameState(GameSession session, Player[] players) {
        return gameConditions.getGameState();
    }

    public Result isWinner(GameSession session, Player[] players) {
        if (gameConditions.isWinner(session, players) == null) {

        }
        return null;
    }

    public Player getWinner(GameSession session, Player[] players) {
        if (gameConditions.getWinner(session, players) == null) {

        }
        return null;
    }

    public abstract void setMove(GameSession session, Move move);
}
