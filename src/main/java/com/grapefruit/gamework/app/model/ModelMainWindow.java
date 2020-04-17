package com.grapefruit.gamework.app.model;

import com.grapefruit.gamework.framework.GameWrapper;
import com.grapefruit.gamework.games.GameRegistry;
import com.grapefruit.gamework.network.ServerManager;

import java.util.ArrayList;

/**
 * The type Model main window.
 */
public class ModelMainWindow implements IModel {

    private ArrayList<GameWrapper> gameWrappers = GameRegistry.GAMES;
    private GameWrapper selectedGameWrapper = null;
    private String[] availableGames;
    private ServerManager serverManager;

    /**
     * Instantiates a new Model main window.
     *
     * @param serverManager the server manager
     */
    public ModelMainWindow(ServerManager serverManager) {
        this.serverManager = serverManager;
    }

    /**
     * Gets games.
     *
     * @return fetches array of games
     */
    public ArrayList<GameWrapper> getGames() {
        return gameWrappers;
    }

    /**
     * Sets selected game.
     *
     * @param game Sets currently selected game to specified game.
     */
    public void setSelectedGame(GameWrapper game) {
        this.selectedGameWrapper = game;
    }

    /**
     * Gets selected game.
     *
     * @return selected game (Can be null if nothing is selected).
     */
    public GameWrapper getSelectedGame() {
        return selectedGameWrapper;
    }


    /**
     * Sets available games.
     *
     * @param availableGames the available games
     */
    public void setAvailableGames(String[] availableGames) {
        this.availableGames = availableGames;
    }

    /**
     * Get available games string [ ].
     *
     * @return the string [ ]
     */
    public String[] getAvailableGames() {
        return availableGames;
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
     * Sets server manager.
     *
     * @param serverManager the server manager
     */
    public void setServerManager(ServerManager serverManager) {
        this.serverManager = serverManager;
    }
}
