package com.grapefruit.gamework.framework;

public interface Minimax {
    Tile calculateBestMove(Board board, Player player, Player opponent, int turnCount);

    void startTimeout(int timeout);

    void destroy();

    void setDepth(int depth);

    void setComplexity(int complexity);
}