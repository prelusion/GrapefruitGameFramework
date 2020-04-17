package com.grapefruit.gamework.app.model;

import com.grapefruit.gamework.framework.Assets;
import com.grapefruit.gamework.framework.Game;
import com.grapefruit.gamework.network.ServerManager;

/**
 * The type Model game.
 */
public class ModelGame implements IModel {

    private Game game;
    private Assets assets;
    private ServerManager serverManager;
    private boolean isTournament;
    private boolean autoChallenge;


    /**
     * Instantiates a new Model game.
     *
     * @param game          the game
     * @param assets        the assets
     * @param manager       the manager
     * @param isTournament  the is tournament
     * @param autoChallenge the auto challenge
     */
    public ModelGame(Game game, Assets assets, ServerManager manager, boolean isTournament, boolean autoChallenge) {
        this.game = game;
        this.assets = assets;
        this.serverManager = manager;
        this.isTournament = isTournament;
        this.autoChallenge = autoChallenge;
    }

    /**
     * Gets game.
     *
     * @return returns Game
     */
    public Game getGame() {
        return game;
    }

    /**
     * Gets assets.
     *
     * @return the assets
     */
    public Assets getAssets() {
        return assets;
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
     * Is online game boolean.
     *
     * @return the boolean
     */
    public boolean isOnlineGame() {
        return serverManager != null;
    }

    /**
     * Is tournament boolean.
     *
     * @return the boolean
     */
    public boolean isTournament() {
        return isTournament;
    }

    ;

    /**
     * Is auto challenge boolean.
     *
     * @return the boolean
     */
    public boolean isAutoChallenge() {
        return autoChallenge;
    }

    ;
}
