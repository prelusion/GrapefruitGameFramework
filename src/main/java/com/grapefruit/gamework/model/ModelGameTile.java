package com.grapefruit.gamework.model;

import com.grapefruit.gamework.controller.IController;
import com.grapefruit.gamework.games.Game;
import javafx.scene.Parent;

public class ModelGameTile implements IModel {

    private Game game;

    public ModelGameTile(Game game){
        this.game = game;
    }

    public Game getGame() {
        return game;
    }
}
