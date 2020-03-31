package com.grapefruit.gamework.framework;

public interface MoveAlgorithm {
    Tile getBestMove(int depth, Board board, int score, int alpha, int beta, boolean maximizingPlayer);
    Tile calculateBestMove(Board board, Player player);
}
