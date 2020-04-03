package com.grapefruit.gamework.app.model;

import com.grapefruit.gamework.app.controller.ControllerSelectedGame;
import com.grapefruit.gamework.framework.network.ServerConnection;
import com.grapefruit.gamework.framework.network.ServerManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModelLobbyBrowser implements IModel {

    private ServerManager serverManager;
    private String onlineName;
    private String gameId;
    private List<ServerConnection.ResponseChallenge> challenges;
    private ControllerSelectedGame controllerSelectedGame;

    public ModelLobbyBrowser(ServerManager serverManager, String onlineName, ControllerSelectedGame controllerSelectedGame){
        this.serverManager = serverManager;
        this.onlineName = onlineName;
        this.controllerSelectedGame = controllerSelectedGame;
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

    public List<ServerConnection.ResponseChallenge> getChallenges() {
        fetchChallenges();
        return challenges;
    }

    public ControllerSelectedGame getControllerSelectedGame() {
        return controllerSelectedGame;
    }
}
