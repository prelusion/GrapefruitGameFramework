package com.grapefruit.gamework.games.othello;

import com.grapefruit.gamework.framework.old.Conditions;
import com.grapefruit.gamework.framework.old.GameState;
import com.grapefruit.gamework.framework.old.MoveSetter;
import com.grapefruit.gamework.framework.template.*;

public class GameOthello extends GameTemplate {
    public GameOthello(Board board, MoveSetter moveSetter, Conditions conditions, Game session, GameState state, Player player) {
        super(board, moveSetter, conditions, session, state, player);
    }
}
