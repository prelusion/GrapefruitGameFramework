package com.grapefruit.gamework.app.model;


import com.grapefruit.gamework.app.games.Game;
import com.grapefruit.gamework.app.games.GameRegistry;

public class ModelMainWindow implements IModel {

    private Game[] games = GameRegistry.games;

    public ModelMainWindow(){

    }

    public Game[] getGames() {
        return games;
    }
}
