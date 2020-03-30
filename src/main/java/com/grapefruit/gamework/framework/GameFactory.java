package com.grapefruit.gamework.framework;

import com.grapefruit.gamework.games.tictactoe.TicTacToe;

public abstract class GameFactory {

    public abstract Game create(Player[] players);

}
