package com.grapefruit.gamework.framework.player;

import com.grapefruit.gamework.framework.*;

public class AIPlayer extends Player {
    private MoveAlgorithm moveAlgorithm;
    private int depth;
    public AIPlayer(String name, Colors color, MoveAlgorithm moveAlgorithm, int depth) {
        super(name, color, false);
        this.moveAlgorithm = moveAlgorithm;
        this.depth = depth;
    }

    public Tile calculateMove(Board board, Player opponent) {
        return moveAlgorithm.calculateBestMove(board, this, opponent, depth);
    }
}
