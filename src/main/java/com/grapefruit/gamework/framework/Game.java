package com.grapefruit.gamework.framework;

import com.grapefruit.gamework.framework.Board;
import com.grapefruit.gamework.framework.Move;
import com.grapefruit.gamework.framework.Rule;

public interface Game {
    Rule rules = new Rule() {
        @Override
        public void setMove(Board board, Move move) {

        }

        @Override
        public boolean validMove(Board board, Move move) {
            return false;
        }
    };

}
