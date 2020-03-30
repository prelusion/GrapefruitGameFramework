package com.grapefruit.gamework.app.model;


import com.grapefruit.gamework.framework.Game;

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

    /**
     *
     * @return returns Game
     */
    public Game getGame() {
        return game;
    }

    /**
     * Sets selected game in main window to model's game.
     */
    public void gameSelected(){
        modelMainWindow.setSelectedGame(game);
    }

    /**
     * Sets select game in main window to null
     */
    public void gameUnselected(){
        modelMainWindow.setSelectedGame(null);
    }

    /**
     *
     * @return Whether game is selected;
     */
    public boolean isSelected(){
        return selected;
    }
}
