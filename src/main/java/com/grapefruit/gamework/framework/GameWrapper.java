package com.grapefruit.gamework.framework;

public class GameWrapper {

    private GameFactory factory;
    private Assets assets;

    public GameWrapper(GameFactory factory, Assets assets){
        this.factory = factory;
        this.assets = assets;
    }

    public GameFactory getFactory() {
        return factory;
    }

    public void setFactory(GameFactory factory) {
        this.factory = factory;
    }

    public Assets getAssets() {
        return assets;
    }

    public void setAssets(Assets assets) {
        this.assets = assets;
    }
}
