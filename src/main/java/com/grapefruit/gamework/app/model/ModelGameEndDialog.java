package com.grapefruit.gamework.app.model;

import com.grapefruit.gamework.framework.Game;

public class ModelGameEndDialog implements IModel {

    private Game game;

    public ModelGameEndDialog(Game game){
        this.game = game;
    }

    public Game getGame() {
        return game;
    }
}
