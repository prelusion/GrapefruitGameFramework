package com.grapefruit.gamework.app.model;

import com.grapefruit.gamework.framework.GameWrapper;
import com.grapefruit.gamework.network.ServerManager;

/**
 * The type Model selected game.
 */
public class ModelSelectedGame implements IModel {

    private GameWrapper selectedGame;
    private ServerManager serverManager;
    private String onlineName;
    private boolean online;

    /**
     * Instantiates a new Model selected game.
     *
     * @param game       the game
     * @param manager    the manager
     * @param onlineName the online name
     * @param online     the online
     */
    public ModelSelectedGame(GameWrapper game, ServerManager manager, String onlineName, boolean online) {
        selectedGame = game;
        serverManager = manager;
        this.onlineName = onlineName;
        this.online = online;
    }

    /**
     * Gets selected game.
     *
     * @return the selected game
     */
    public GameWrapper getSelectedGame() {
        return selectedGame;
    }

    /**
     * Gets server manager.
     *
     * @return the server manager
     */
    public ServerManager getServerManager() {
        return serverManager;
    }

    /**
     * Gets online name.
     *
     * @return the online name
     */
    public String getOnlineName() {
        return onlineName;
    }

    /**
     * Is online boolean.
     *
     * @return the boolean
     */
    public boolean isOnline() {
        return online;
    }
}
