package com.grapefruit.gamework.app.model;

import com.grapefruit.gamework.framework.Game;
import com.grapefruit.gamework.framework.GameWrapper;

public class ModelSelectedGame implements IModel {

    private GameWrapper selectedGame;

    public ModelSelectedGame(GameWrapper game){
        selectedGame = game;
    }

    public GameWrapper getSelectedGame() {
        return selectedGame;
    }
}
