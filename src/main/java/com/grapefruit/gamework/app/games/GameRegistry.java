package com.grapefruit.gamework.app.games;

import com.grapefruit.gamework.app.games.othello.GameOthello;
import com.grapefruit.gamework.app.games.othello.GameTicTacToe;


public class GameRegistry {

    public static Game[] games = {
            new GameOthello(),
            new GameTicTacToe(),
            new GameOthello(),
            new GameTicTacToe(),
            new GameOthello(),
            new GameTicTacToe()
        };

}
