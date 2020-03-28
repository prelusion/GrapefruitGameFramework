package com.grapefruit.gamework.app.model;

import com.grapefruit.gamework.app.games.Game;

public class ModelSelectedGame implements IModel {

    private Game selectedGame;

    public ModelSelectedGame(Game game){
        selectedGame = game;
    }

    public Game getSelectedGame() {
        return selectedGame;
    }
}
