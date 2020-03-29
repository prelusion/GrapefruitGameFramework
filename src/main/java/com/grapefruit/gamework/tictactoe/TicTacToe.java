package com.grapefruit.gamework.tictactoe;

import com.grapefruit.gamework.app.resources.ImageRegistry;
import com.grapefruit.gamework.framework.*;

public class TicTacToe extends Game {
    public TicTacToe(MoveSetter moveSetter) {
        super(moveSetter, new Board(3), "Tic Tac Toe", ImageRegistry.GAME_ICON_TICTACTOE);
    }


//    public GameSession createSession() {
//
//    }
}
