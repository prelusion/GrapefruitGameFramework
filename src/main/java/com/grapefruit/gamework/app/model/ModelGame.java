package com.grapefruit.gamework.app.model;

import com.grapefruit.gamework.framework.Assets;
import com.grapefruit.gamework.framework.Game;
import com.grapefruit.gamework.framework.GameWrapper;
import com.grapefruit.gamework.framework.Player;
import com.grapefruit.gamework.framework.network.ServerManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModelGame implements IModel {

    private Game game;
    private Assets assets;
    private ServerManager serverManager;

    public ModelGame(Game game, Assets assets, ServerManager manager){
        this.game = game;
        this.assets = assets;
        this.serverManager = manager;
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
}
