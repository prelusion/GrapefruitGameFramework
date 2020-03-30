package com.grapefruit.gamework.games.tictactoe;

import com.grapefruit.gamework.framework.old.MoveSetter;
import com.grapefruit.gamework.framework.template.*;

public class TTTFactory {

    private TTTFactory() {}

    public static void create(Player[] players) {
        Condition[] conditions = new Condition[] {new TTTCondition1()};
        MoveSetter moveSetter = new TTTMoveSetter();
        Board board = new Board(9);
        int turnTimeout = 10;
//        Game session = new OfflineGame(board, moveSetter, players, turnTimeout);
//        return new TTT(board, moveSetter, conditions, session, players);
    }
}
