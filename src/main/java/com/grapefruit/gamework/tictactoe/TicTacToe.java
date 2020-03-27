package com.grapefruit.gamework.tictactoe;

import com.grapefruit.gamework.framework.*;

public class TicTacToe extends Game {
    public TicTacToe() {
        super();
    }


    public GameSession createSession(MoveSetter moveSetter, Player[] players) {
        return new OfflineGameSession(moveSetter, players,10, 9);
    }
}
