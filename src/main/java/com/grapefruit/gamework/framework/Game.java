package com.grapefruit.gamework.framework;

import com.grapefruit.gamework.framework.Board;
import com.grapefruit.gamework.framework.Move;
import com.grapefruit.gamework.framework.Rule;

public abstract class Game {
    private Rule[] rules;

    public Game(Rule[] rules) {
        rules = rules;
    }

    public boolean isValidMove(Board board, Move move) {
        for (Rule rule : rules) {
            if (!rule.validMove(board, move)) return false;
        }
        return true;
    }

    public GameSession createSession(Player[] players) {
        return new OfflineGameSession(players, rules, 10, 9);
    }
}
