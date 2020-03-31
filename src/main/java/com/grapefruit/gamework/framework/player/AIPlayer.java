package com.grapefruit.gamework.framework.player;

import com.grapefruit.gamework.framework.Board;
import com.grapefruit.gamework.framework.Colors;
import com.grapefruit.gamework.framework.MoveAlgorithm;
import com.grapefruit.gamework.framework.Tile;

public class AIPlayer extends Player {
    private MoveAlgorithm moveAlgorithm;
    public AIPlayer(String name, Colors color, MoveAlgorithm moveAlgorithm) {
        super(name, color, false);
        this.moveAlgorithm = moveAlgorithm;
    }

    public Tile calculateMove(Board board) {
        return moveAlgorithm.calculateBestMove(board, this);
    }
}
