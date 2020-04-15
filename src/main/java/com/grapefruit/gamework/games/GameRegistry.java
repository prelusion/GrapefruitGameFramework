package com.grapefruit.gamework.games;

import com.grapefruit.gamework.framework.GameWrapper;
import com.grapefruit.gamework.games.reversi.ReversiAssets;
import com.grapefruit.gamework.games.reversi.ReversiFactory;
import com.grapefruit.gamework.games.tictactoe.TicTacToeAssets;
import com.grapefruit.gamework.games.tictactoe.TicTacToeFactory;

import java.util.ArrayList;

public abstract class GameRegistry {

    private static GameWrapper REVERSI = new GameWrapper(new ReversiFactory(), new ReversiAssets());
    private static GameWrapper TICTACTOE = new GameWrapper(new TicTacToeFactory(), new TicTacToeAssets());

    public static final ArrayList<GameWrapper> GAMES = new ArrayList<GameWrapper>() {{
        add(REVERSI);
        add(TICTACTOE);
    }};

}
