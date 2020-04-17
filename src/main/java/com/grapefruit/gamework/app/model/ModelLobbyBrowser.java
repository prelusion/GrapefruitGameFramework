package com.grapefruit.gamework.app.model;

import com.grapefruit.gamework.app.controller.ControllerSelectedGame;
import com.grapefruit.gamework.framework.Assets;
import com.grapefruit.gamework.framework.GameWrapper;
import com.grapefruit.gamework.network.CommandCallback;
import com.grapefruit.gamework.network.Commands;
import com.grapefruit.gamework.network.ServerConnection;
import com.grapefruit.gamework.network.ServerManager;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Model lobby browser.
 */
public class ModelLobbyBrowser implements IModel {

    private ServerManager serverManager;
    private String onlineName;
    private String gameId;
    private List<ServerConnection.ResponseChallenge> challenges;
    private List<String> playerNames;
    private ControllerSelectedGame controllerSelectedGame;
    private Assets gameAssets;
    private GameWrapper selectedGame;

    /**
     * Instantiates a new Model lobby browser.
     *
     * @param serverManager          the server manager
     * @param onlineName             the online name
     * @param controllerSelectedGame the controller selected game
     * @param gameAssets             the game assets
     * @param selectedGame           the selected game
     */
    public ModelLobbyBrowser(ServerManager serverManager, String onlineName, ControllerSelectedGame controllerSelectedGame, Assets gameAssets, GameWrapper selectedGame) {
        this.serverManager = serverManager;
        this.onlineName = onlineName;
        this.controllerSelectedGame = controllerSelectedGame;
        this.gameAssets = gameAssets;
        this.selectedGame = selectedGame;
        fetchChallenges();
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
     * Gets server manager.
     *
     * @return the server manager
     */
    public ServerManager getServerManager() {
        return serverManager;
    }

    /**
     * Fetch challenges.
     */
    public void fetchChallenges() {
        challenges = serverManager.getChallenges();
    }

    /**
     * Fetch players.
     */
    public void fetchPlayers() {
        serverManager.queueCommand(Commands.getPlayerList(new CommandCallback() {
            @Override
            public void onResponse(boolean success, String[] args) {
                if (success) {
                    setPlayerNames(args);
                }
            }
        }));
    }

    /**
     * Sets player names.
     *
     * @param playerNames the player names
     */
    public synchronized void setPlayerNames(String[] playerNames) {
        List<String> names = new ArrayList<>();
        for (String name : playerNames) {
            names.add(name);
        }
        this.playerNames = names;
    }

    /**
     * Gets challenges.
     *
     * @return the challenges
     */
    public List<ServerConnection.ResponseChallenge> getChallenges() {
        fetchChallenges();
        return challenges;
    }

    /**
     * Gets controller selected game.
     *
     * @return the controller selected game
     */
    public ControllerSelectedGame getControllerSelectedGame() {
        return controllerSelectedGame;
    }

    /**
     * Gets player names.
     *
     * @return the player names
     */
    public List<String> getPlayerNames() {
        return playerNames;
    }

    /**
     * Gets game assets.
     *
     * @return the game assets
     */
    public Assets getGameAssets() {
        return gameAssets;
    }

    /**
     * Gets selected game.
     *
     * @return the selected game
     */
    public GameWrapper getSelectedGame() {
        return selectedGame;
    }
}
