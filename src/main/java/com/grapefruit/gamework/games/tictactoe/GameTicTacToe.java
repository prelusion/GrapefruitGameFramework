package com.grapefruit.gamework.games.tictactoe;

import com.grapefruit.gamework.framework.GameTemplate;
import com.grapefruit.gamework.framework.template.*;

public class GameTicTacToe extends GameTemplate {

    public GameTicTacToe(Board board, MoveSetter moveSetter, Conditions conditions, GameSession session, GameState state, Player player) {
        super(board, moveSetter, conditions, session, state, player);
    }



}
