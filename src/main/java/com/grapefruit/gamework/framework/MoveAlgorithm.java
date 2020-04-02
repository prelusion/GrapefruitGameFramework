package com.grapefruit.gamework.framework;

import com.grapefruit.gamework.framework.player.Player;

public interface MoveAlgorithm {
    int minimax(int depth, Board board, Tile tile, int alpha, int beta, Player player, Player opponentPlayer, boolean maximizingPlayer);
    Tile calculateBestMove(Board board, Player player, Player opponent, int depth);
}
