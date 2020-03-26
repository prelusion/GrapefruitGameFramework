package com.grapefruit.gamework.app.model;


import com.grapefruit.gamework.app.games.Game;
import com.grapefruit.gamework.app.games.GameRegistry;

public class ModelMainWindow implements IModel {

    private Game[] games = GameRegistry.games;
    private Game selectedGame = null;

    public ModelMainWindow(){

    }

    public Game[] getGames() {
        return games;
    }

    public void setSelectedGame(Game game){
        this.selectedGame = game;
    }

    public Game getSelectedGame(){
        return selectedGame;
    }
}
