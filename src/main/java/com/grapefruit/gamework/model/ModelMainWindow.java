package com.grapefruit.gamework.model;


import com.grapefruit.gamework.games.Game;
import com.grapefruit.gamework.games.GameRegistry;

public class ModelMainWindow implements IModel {

    private Game[] games = GameRegistry.games;

    public ModelMainWindow(){

    }

    public Game[] getGames() {
        return games;
    }
}
