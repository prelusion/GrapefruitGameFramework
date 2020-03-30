package com.grapefruit.gamework.framework;

import javafx.scene.image.Image;

public abstract class Game {
    /**
     * The moveSetter is a variable Object that saves the rules and conditions of the game.
     */
    private MoveSetter moveSetter;

    /**
     * @param moveSetter sets the game moveSetter within the global variable.
     */
    public Game(MoveSetter moveSetter) {
        this.moveSetter = moveSetter;
        this.board = board;
        this.assets = assets;
    }

    /**
     * @param players gives the players to the gameSession.
     * @return gameSession with the moveSetter, players, turnTimeout and boardSize.
     */
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
