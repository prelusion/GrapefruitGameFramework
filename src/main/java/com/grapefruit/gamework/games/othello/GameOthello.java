package com.grapefruit.gamework.games.othello;

import com.grapefruit.gamework.framework.GameTemplate;
import com.grapefruit.gamework.framework.template.*;

public class GameOthello extends GameTemplate {
    public GameOthello(Board board, MoveSetter moveSetter, Conditions conditions, GameSession session, GameState state, Player player) {
        super(board, moveSetter, conditions, session, state, player);
    }
}
