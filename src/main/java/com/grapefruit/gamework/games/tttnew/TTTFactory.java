package com.grapefruit.gamework.games.tttnew;

import com.grapefruit.gamework.framework.old.MoveSetter;
import com.grapefruit.gamework.framework.template.*;
import com.grapefruit.gamework.games.tictactoe.TTTCondition1;
import com.grapefruit.gamework.games.tictactoe.TTTMoveSetter;

public class TTTFactory {
    public static TTTGame create(Player[] players) {
        Board board = new Board(9);
        int turnTimeout = 10;
        return new TTTGame(board, players, turnTimeout);
    }
}
