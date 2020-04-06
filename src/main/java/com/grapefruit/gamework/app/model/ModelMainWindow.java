package com.grapefruit.gamework.app.model;

import com.grapefruit.gamework.framework.GameWrapper;
import com.grapefruit.gamework.framework.network.ServerManager;
import com.grapefruit.gamework.games.GameRegistry;

import java.util.ArrayList;

public class ModelMainWindow implements IModel {

    private ArrayList<GameWrapper> gameWrappers = GameRegistry.GAMES;
    private GameWrapper selectedGameWrapper = null;
    private String[] availableGames;
    private ServerManager serverManager;

    public ModelMainWindow(){
    }

    /**
     * @return fetches array of games
     */
    public ArrayList<GameWrapper> getGames() {
        return gameWrappers;
    }

    /**
     * @param game
     * Sets currently selected game to specified game.
     */
    public void setSelectedGame(GameWrapper game){
        this.selectedGameWrapper = game;
    }

    /**
     *
     * @return selected game (Can be null if nothing is selected).
     */
    public GameWrapper getSelectedGame(){
        return selectedGameWrapper;
    }


    public void setAvailableGames(String[] availableGames) {
        this.availableGames = availableGames;
    }

    public String[] getAvailableGames() {
        return availableGames;
    }

    public ServerManager getServerManager() {
        return serverManager;
    }

    public void setServerManager(ServerManager serverManager) {
        this.serverManager = serverManager;
    }
}
