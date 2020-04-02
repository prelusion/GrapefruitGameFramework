package com.grapefruit.gamework.framework;

import com.grapefruit.gamework.framework.player.Player;

public interface MoveAlgorithm {
    int getBestMoveValue(int depth, Board board, Tile tile, int alpha, int beta, Player player, boolean maximizingPlayer);
    Tile calculateBestMove(Board board, Player player, int depth);
}
