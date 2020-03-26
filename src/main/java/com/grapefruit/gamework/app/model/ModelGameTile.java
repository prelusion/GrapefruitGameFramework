package com.grapefruit.gamework.app.model;

import com.grapefruit.gamework.app.games.Game;

public class ModelGameTile implements IModel {

    private Game game;

    public ModelGameTile(Game game){
        this.game = game;
    }

    public Game getGame() {
        return game;
    }
}
