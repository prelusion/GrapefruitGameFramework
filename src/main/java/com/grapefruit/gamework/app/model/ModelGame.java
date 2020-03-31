package com.grapefruit.gamework.app.model;

import com.grapefruit.gamework.framework.Assets;
import com.grapefruit.gamework.framework.Game;
import com.grapefruit.gamework.framework.GameWrapper;

public class ModelGame implements IModel {

    private Game game;
    private Assets assets;
    private boolean selected;

    public ModelGame(Game game, Assets assets){
        this.game = game;
        this.assets = assets;
    }

    /**
     *
     * @return returns Game
     */
    public Game getGame() {
        return game;
    }

    public Assets getAssets(){
        return assets;
    }
}
