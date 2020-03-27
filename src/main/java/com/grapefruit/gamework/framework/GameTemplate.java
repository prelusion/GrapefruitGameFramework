package com.grapefruit.gamework.framework;

import com.grapefruit.gamework.framework.template.*;

public abstract class GameTemplate {

    public Board board;
    public MoveSetter moveSetter;
    public Conditions conditions;
    public GameSession session;
    public GameState state;
    public Player player;

    public GameTemplate(Board board, MoveSetter moveSetter,Conditions conditions, GameSession session, GameState state, Player player) {
        this.board = board;
        this.moveSetter = moveSetter;
        this.conditions = conditions;
        this.session = session;
        this.state = state;
        this.player = player;
    }
}
