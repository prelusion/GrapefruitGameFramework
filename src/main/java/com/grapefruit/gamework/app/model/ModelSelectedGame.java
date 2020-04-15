package com.grapefruit.gamework.app.model;

import com.grapefruit.gamework.framework.GameWrapper;
import com.grapefruit.gamework.network.ServerManager;

public class ModelSelectedGame implements IModel {

    private GameWrapper selectedGame;
    private ServerManager serverManager;
    private String onlineName;
    private boolean online;

    public ModelSelectedGame(GameWrapper game, ServerManager manager, String onlineName, boolean online) {
        selectedGame = game;
        serverManager = manager;
        this.onlineName = onlineName;
        this.online = online;
    }

    public GameWrapper getSelectedGame() {
        return selectedGame;
    }

    public ServerManager getServerManager() {
        return serverManager;
    }

    public String getOnlineName() {
        return onlineName;
    }

    public boolean isOnline() {
        return online;
    }
}
