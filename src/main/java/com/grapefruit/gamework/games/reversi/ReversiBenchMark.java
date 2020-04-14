package com.grapefruit.gamework.games.reversi;

import com.grapefruit.gamework.framework.*;

import static com.grapefruit.gamework.games.reversi.ReversiFactory.STRATEGIC_VALUES;

public class ReversiBenchMark implements BenchMark {
    public int turnTimeout;
    private MinimaxAlgorithm minimax;
    private Board board;
    private Player player;
    private Player opponent;

    public ReversiBenchMark(Game game) {
        this.turnTimeout = game.getTurnTimeout();
        this.minimax = game.getMinimaxAlgorithm();
        this.player = game.getCurrentPlayer();
        this.opponent = game.getOpponentPlayer();
        board = new ReversiBoard(8, STRATEGIC_VALUES);
        initWorstCaseBoard();
    }

    public int calculateDepth(int depth) {
        System.out.println(depth);
        long startTime = System.currentTimeMillis()/1000;
        Tile tile = minimax.calculateBestMove(board, player, opponent, 5);
        long endTime = System.currentTimeMillis()/1000;

        if((endTime - startTime) < turnTimeout && tile != null) {
            return depth;
        }
        return 3;
    }

    public void initWorstCaseBoard() {
        board.grid[1][1].setPlayer(opponent);
        board.grid[1][4].setPlayer(opponent);

        board.grid[2][0].setPlayer(player);
        board.grid[2][1].setPlayer(opponent);
        board.grid[2][2].setPlayer(opponent);
        board.grid[2][3].setPlayer(player);
        board.grid[2][4].setPlayer(opponent);
        board.grid[2][5].setPlayer(opponent);


        board.grid[3][1].setPlayer(opponent);
        board.grid[3][2].setPlayer(opponent);
        board.grid[3][3].setPlayer(opponent);
        board.grid[3][4].setPlayer(opponent);
        board.grid[3][5].setPlayer(player);
        board.grid[3][6].setPlayer(opponent);

        board.grid[4][1].setPlayer(opponent);
        board.grid[4][2].setPlayer(player);
        board.grid[4][3].setPlayer(player);
        board.grid[4][4].setPlayer(player);
        board.grid[4][5].setPlayer(opponent);

        board.grid[5][1].setPlayer(opponent);
        board.grid[5][2].setPlayer(opponent);
        board.grid[5][3].setPlayer(player);
        board.grid[5][4].setPlayer(opponent);
        board.grid[5][5].setPlayer(opponent);

        board.grid[6][3].setPlayer(player);

        board.grid[7][2].setPlayer(opponent);
        board.grid[7][3].setPlayer(player);
    }
}
