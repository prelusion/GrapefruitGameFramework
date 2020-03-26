package com.grapefruit.gamework.app.model;

import com.grapefruit.gamework.app.games.Game;

public class ModelGameTile implements IModel {

    private Game game;
    private ModelMainWindow modelMainWindow;
    private boolean selected;

    public ModelGameTile(Game game, ModelMainWindow mainWindow){
        this.game = game;
        this.modelMainWindow = mainWindow;
        if (modelMainWindow.getSelectedGame() != null){
            if (modelMainWindow.getSelectedGame().getName() == game.getName()){
                selected = true;
            }
        }
    }

    public Game getGame() {
        return game;
    }

    public void gameSelected(){
        modelMainWindow.setSelectedGame(game);
    }

    public void gameUnselected(){
        modelMainWindow.setSelectedGame(null);
    }

    public boolean isSelected(){
        return selected;
    }
}
