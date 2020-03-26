package com.grapefruit.gamework.framework;

import com.grapefruit.gamework.framework.Board;
import com.grapefruit.gamework.framework.Move;

public interface Rule {
    public boolean validMove(Board board, Move move);
}
