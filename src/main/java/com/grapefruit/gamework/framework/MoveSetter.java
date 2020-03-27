package com.grapefruit.gamework.framework;

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

    public String getGameResult(GameSession session, Player[] players) {
        return gameConditions.getGameResult(session, players);
    }

    public Player getWinner(GameSession session, Player[] players) {
        for (Player player: players) {
            return gameConditions.getWinner(session, player);
        }
        return null;
    }

    public abstract void setMove(GameSession session, Move move);
}
