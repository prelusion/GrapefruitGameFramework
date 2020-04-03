package com.grapefruit.gamework.framework;

import com.grapefruit.gamework.framework.player.Player;

public abstract class GameFactory {

    public abstract Game create(Player[] players);

}
