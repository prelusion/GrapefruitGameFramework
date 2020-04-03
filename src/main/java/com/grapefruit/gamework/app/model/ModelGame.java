package com.grapefruit.gamework.app.model;

import com.grapefruit.gamework.framework.Assets;
import com.grapefruit.gamework.framework.Game;
import com.grapefruit.gamework.framework.GameWrapper;
import com.grapefruit.gamework.framework.network.ServerManager;
import com.grapefruit.gamework.framework.player.LocalPlayer;
import com.grapefruit.gamework.framework.player.Player;

import java.util.ArrayList;
import java.util.List;

public class ModelGame implements IModel {

    private Game game;
    private Assets assets;
    private List<Player> localPlayers;
    private ServerManager serverManager;

    public ModelGame(Game game, Assets assets, Player[] localPlayers, ServerManager manager){
        this.game = game;
        this.assets = assets;
        this.serverManager = manager;

        List<Player> players = new ArrayList<>();
        for (Player player: localPlayers){
            players.add(player);
        }
        this.localPlayers = players;
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

    public List<Player> getLocalPlayers() {
        return localPlayers;
    }

    public ServerManager getServerManager() {
        return serverManager;
    }
}
