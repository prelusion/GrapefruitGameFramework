package com.grapefruit.gamework.app.model;


import com.grapefruit.gamework.app.GameRegistry;
import com.grapefruit.gamework.framework.Game;

public class ModelMainWindow implements IModel {

    private Game[] games = GameRegistry.Games;
    private Game selectedGame = null;

    public ModelMainWindow(){
    }

    /**
     * @return fetches array of games
     */
    public Game[] getGames() {
        return games;
    }

    /**
     * @param game
     * Sets currently selected game to specified game.
     */
    public void setSelectedGame(Game game){
        this.selectedGame = game;
    }

    /**
     *
     * @return selected game (Can be null if nothing is selected).
     */
    public Game getSelectedGame(){
        return selectedGame;
    }
}
