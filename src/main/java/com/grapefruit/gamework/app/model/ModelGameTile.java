package com.grapefruit.gamework.app.model;


import com.grapefruit.gamework.framework.Game;
import com.grapefruit.gamework.framework.GameWrapper;

public class ModelGameTile implements IModel {

    private GameWrapper game;
    private ModelMainWindow modelMainWindow;
    private boolean selected;
    private boolean availableOnServer;

    public ModelGameTile(GameWrapper game, ModelMainWindow mainWindow){
        this.game = game;
        this.modelMainWindow = mainWindow;
        if (modelMainWindow.getSelectedGame() != null){
            if (modelMainWindow.getSelectedGame() == game){
                selected = true;
            }
        }
        if (mainWindow.getAvailableGames() != null && mainWindow.getAvailableGames().length > 0){
            for (String availableGame: mainWindow.getAvailableGames()){
                 if (game.getAssets().getServerId().equals(availableGame)){
                     availableOnServer = true;
                 }
            }
        } else {
            availableOnServer = false;
        }
    }

    public boolean isAvailableOnServer(){
        return availableOnServer;
    }

    /**
     *
     * @return returns Game
     */
    public GameWrapper getGame() {
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
