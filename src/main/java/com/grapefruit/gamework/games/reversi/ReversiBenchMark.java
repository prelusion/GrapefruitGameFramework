package com.grapefruit.gamework.games.reversi;

import com.grapefruit.gamework.framework.MinimaxAlgorithm;

import com.grapefruit.gamework.framework.*;

import java.util.concurrent.ExecutorService;

import static com.grapefruit.gamework.games.reversi.ReversiFactory.STRATEGIC_VALUES;

public class ReversiBenchMark {
    public int turnTimeout;
    private MinimaxAlgorithm minimax;
    private Board board;
    private Player player;
    private Player opponent;

    public ReversiBenchMark(int turnTimeout, MinimaxAlgorithm minimax, Player player, Player opponent) {
        this.turnTimeout = turnTimeout;
        this.minimax = minimax;
        this.player = player;
        this.opponent = opponent;
        board = new ReversiBoard(8, STRATEGIC_VALUES);
        initWorstCaseBoard();
    }

    public int calculateDepth(int depth) {
        System.out.println(depth);
        long startTime = System.currentTimeMillis()/1000;
        Tile tile = minimax.calculateBestMove(board, player, opponent, 5);
        long endTime = System.currentTimeMillis()/1000;

        if((endTime - startTime) < turnTimeout && tile != null) {
            return calculateDepth(depth + 1);
        }
        System.out.println("Depth " + depth);
        return depth - 1;
    }

    public void initWorstCaseBoard() {

        board.grid[3][3].setPlayer(player);
        board.grid[3][4].setPlayer(opponent);
        board.grid[4][3].setPlayer(opponent);
        board.grid[4][4].setPlayer(player);

//        board.grid[1][1].setPlayer(opponent);
//        board.grid[1][4].setPlayer(opponent);

//        board.grid[2][0].setPlayer(player);
//        board.grid[2][1].setPlayer(opponent);
//        board.grid[2][2].setPlayer(opponent);
//        board.grid[2][3].setPlayer(player);
//        board.grid[2][4].setPlayer(opponent);
//        board.grid[2][5].setPlayer(opponent);
//
//
//        board.grid[3][1].setPlayer(opponent);
//        board.grid[3][2].setPlayer(opponent);
//        board.grid[3][3].setPlayer(opponent);
//        board.grid[3][4].setPlayer(opponent);
//        board.grid[3][5].setPlayer(player);
//        board.grid[3][6].setPlayer(opponent);
//
//        board.grid[4][1].setPlayer(opponent);
//        board.grid[4][2].setPlayer(player);
//        board.grid[4][3].setPlayer(player);
//        board.grid[4][4].setPlayer(player);
//        board.grid[4][5].setPlayer(opponent);
//
//        board.grid[5][1].setPlayer(opponent);
//        board.grid[5][2].setPlayer(opponent);
//        board.grid[5][3].setPlayer(player);
//        board.grid[5][4].setPlayer(opponent);
//        board.grid[5][5].setPlayer(opponent);
//
//        board.grid[6][3].setPlayer(player);
//
//        board.grid[7][2].setPlayer(opponent);
//        board.grid[7][3].setPlayer(player);
    }
}
