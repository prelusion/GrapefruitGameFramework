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

public class ModelLobbyBrowser implements IModel {

    private ServerManager serverManager;
    private String onlineName;
    private String gameId;
    private List<ServerConnection.ResponseChallenge> challenges;
    private List<String> playerNames;
    private ControllerSelectedGame controllerSelectedGame;
    private Assets gameAssets;
    private GameWrapper selectedGame;

    public ModelLobbyBrowser(ServerManager serverManager, String onlineName, ControllerSelectedGame controllerSelectedGame, Assets gameAssets, GameWrapper selectedGame){
        this.serverManager = serverManager;
        this.onlineName = onlineName;
        this.controllerSelectedGame = controllerSelectedGame;
        this.gameAssets = gameAssets;
        this.selectedGame = selectedGame;
        fetchChallenges();
    }


    public String getOnlineName() {
        return onlineName;
    }

    public ServerManager getServerManager() {
        return serverManager;
    }

    public void fetchChallenges(){
        challenges =  serverManager.getChallenges();
    }

    public void fetchPlayers(){
        serverManager.queueCommand(Commands.getPlayerList(new CommandCallback() {
            @Override
            public void onResponse(boolean success, String[] args) {
                if (success) {
                    setPlayerNames(args);
                }
            }
        }));
    }

    public synchronized void setPlayerNames(String[] playerNames) {
        List<String> names = new ArrayList<>();
        for (String name: playerNames){
            names.add(name);
        }
        this.playerNames = names;
    }

    public List<ServerConnection.ResponseChallenge> getChallenges() {
        fetchChallenges();
        return challenges;
    }

    public ControllerSelectedGame getControllerSelectedGame() {
        return controllerSelectedGame;
    }

    public List<String> getPlayerNames() {
        return playerNames;
    }

    public Assets getGameAssets() {
        return gameAssets;
    }

    public GameWrapper getSelectedGame() {
        return selectedGame;
    }
}
