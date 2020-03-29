package com.grapefruit.gamework.framework;

import javafx.scene.image.Image;

public abstract class Game {

    private MoveSetter moveSetter;
    private Board board;
    private String name;
    private Image icon;

    public Game(MoveSetter moveSetter, Board board, String name, Image icon) {
        this.moveSetter = moveSetter;
        this.board = board;
        this.name = name;
        this.icon = icon;
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
}
