package com.grapefruit.gamework.app.model;

import com.grapefruit.gamework.framework.Game;

public class ModelGame implements IModel {

    private Game game;
    private boolean selected;

    public ModelGame(Game game){
        this.game = game;

    }

    /**
     *
     * @return returns Game
     */
    public Game getGame() {
        return game;
    }

}
