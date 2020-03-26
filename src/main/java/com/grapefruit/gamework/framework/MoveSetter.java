package com.grapefruit.gamework.framework;

public abstract class MoveSetter {
    private Rule[] rules;

    public MoveSetter(Rule[] rules) {
        this.rules = rules;
    }

    public boolean isValidMove(Board board, Move move) {
        for (Rule rule : rules) {
            if (!rule.validMove(board, move)) return false;
        }
        return true;
    }

    public abstract void setMove(Board board, Move move);
}
