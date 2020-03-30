package com.grapefruit.gamework.app;

import com.grapefruit.gamework.framework.Game;
import com.grapefruit.gamework.reversi.Reversi;
import com.grapefruit.gamework.reversi.ReversiMoveSetter;
import com.grapefruit.gamework.tictactoe.TTTGameConditions;
import com.grapefruit.gamework.tictactoe.TTTMoveSetter;
import com.grapefruit.gamework.tictactoe.TTTRules;
import com.grapefruit.gamework.tictactoe.TicTacToe;

import java.util.List;

public abstract class GameRegistry {

    public static Game REVERSI = new Reversi(new ReversiMoveSetter());
    //public static Game TICTACTOE = new TicTacToe(new TTTMoveSetter(TTTRules, TTTGameConditions));


    public static Game[] Games = new Game[]{
            REVERSI
    };
}
