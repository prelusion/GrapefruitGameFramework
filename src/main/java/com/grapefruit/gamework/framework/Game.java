package com.grapefruit.gamework.framework;

import javafx.scene.image.Image;

public abstract class Game {

    protected MoveSetter moveSetter;
    protected Board board;
    protected String name;
    protected Image icon;
    protected Assets assets;

    public Game(MoveSetter moveSetter, Board board, String name, Image icon, Assets assets) {
        this.moveSetter = moveSetter;
        this.board = board;
        this.name = name;
        this.icon = icon;
        this.assets = assets;
    }

    public GameSession createSession(Player[] players) {
        return new OfflineGameSession(moveSetter, players, 10, 9);
    }

    public Board getBoard(){
        return board;
    }


    public Image getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }

    public Assets getAssets() {
        return assets;
    }

    public MoveSetter getMoveSetter() {
        return moveSetter;
    }
}
