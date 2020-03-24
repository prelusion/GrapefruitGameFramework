package com.grapefruit.gamework.games;

import com.grapefruit.gamework.games.othello.GameOthello;
import com.grapefruit.gamework.games.othello.GameTicTacToe;

import java.util.List;

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
