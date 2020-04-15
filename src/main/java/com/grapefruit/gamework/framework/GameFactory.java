package com.grapefruit.gamework.framework;

public abstract class GameFactory {

    public abstract Game create(Player[] players);

    public abstract Game create(Player[] players, int difficulty);
}
