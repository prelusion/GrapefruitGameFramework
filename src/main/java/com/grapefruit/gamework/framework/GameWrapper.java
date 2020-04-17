package com.grapefruit.gamework.framework;

/**
 * The type Game wrapper.
 */
public class GameWrapper {

    private GameFactory factory;
    private Assets assets;

    /**
     * Instantiates a new Game wrapper.
     * This is a wrapper to contain and manager the factory and assets in a easy way.
     *
     * @param factory the factory
     * @param assets  the assets
     */
    public GameWrapper(GameFactory factory, Assets assets) {
        this.factory = factory;
        this.assets = assets;
    }

    /**
     * Gets factory.
     *
     * @return the factory
     */
    public GameFactory getFactory() {
        return factory;
    }

    /**
     * Gets assets.
     *
     * @return the assets
     */
    public Assets getAssets() {
        return assets;
    }
}
