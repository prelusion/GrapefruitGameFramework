package com.grapefruit.gamework.app.model;

import com.grapefruit.gamework.framework.Assets;
import com.grapefruit.gamework.framework.Game;
import com.grapefruit.gamework.framework.network.ServerManager;

public class ModelGame implements IModel {

    private Game game;
    private Assets assets;
    private ServerManager serverManager;
    private boolean isTournament;
    private boolean autoChallenge;


    public ModelGame(Game game, Assets assets, ServerManager manager, boolean isTournament, boolean autoChallenge){
        this.game = game;
        this.assets = assets;
        this.serverManager = manager;
        this.isTournament = isTournament;
        this.autoChallenge = autoChallenge;
    }

    /**
     *
     * @return returns Game
     */
    public Game getGame() {
        return game;
    }

    public Assets getAssets(){
        return assets;
    }

    public ServerManager getServerManager() {
        return serverManager;
    }

    public boolean isOnlineGame() {
        return serverManager != null;
    }

    public boolean isTournament() { return isTournament; };

    public boolean isAutoChallenge() { return autoChallenge; };
}
