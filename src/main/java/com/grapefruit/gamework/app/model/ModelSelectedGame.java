package com.grapefruit.gamework.app.model;

import com.grapefruit.gamework.framework.Game;
import com.grapefruit.gamework.framework.GameWrapper;
import com.grapefruit.gamework.framework.network.ServerManager;

public class ModelSelectedGame implements IModel {

    private GameWrapper selectedGame;
    private ServerManager serverManager;

    public ModelSelectedGame(GameWrapper game, ServerManager manager){
        selectedGame = game;
        serverManager = manager;
    }

    public GameWrapper getSelectedGame() {
        return selectedGame;
    }

    public ServerManager getServerManager() {
        return serverManager;
    }
}
