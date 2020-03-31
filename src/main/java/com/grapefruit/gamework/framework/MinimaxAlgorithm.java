package com.grapefruit.gamework.framework;

import java.util.HashSet;

public class MinimaxAlgorithm implements MoveAlgorithm {
    public MinimaxAlgorithm() {

    }


    @Override
    public Tile calculateBestMove(Board board, Player player) {
        HashSet<Tile> tiles = board.getAvailableMoves(player);
        System.out.println(tiles);

        return null;
    }


    @Override
    public Tile getBestMove(int depth, Board board, int score, int alpha, int beta, boolean maximizingPlayer) {
        return null;
    }

}
