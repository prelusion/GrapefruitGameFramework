package com.grapefruit.gamework.games.reversi;

import com.grapefruit.gamework.framework.Board;
import com.grapefruit.gamework.framework.Game;
import com.grapefruit.gamework.framework.GameFactory;
import com.grapefruit.gamework.framework.Player;

public class ReversiFactory extends GameFactory {
    static final int DEFAULT_REVERSI_BOARD_SIZE = 8;

    public static final int[][] STRATEGIC_VALUES = getStrategicValues();

    @Override
    public Game create(Player[] players) {
        return this.create(players[0], players[1]);
    }

    public Reversi create(Player playerBlack, Player playerWhite) {
        Board board = new ReversiBoard(DEFAULT_REVERSI_BOARD_SIZE, STRATEGIC_VALUES);
        int turnTimeout = 60;
        return new Reversi(board, playerBlack, playerWhite, turnTimeout);
    }

    private static int[][] getStrategicValues() {
        int[][] strat = new int[8][8];

        strat[0][0] = 99;
        strat[0][1] = -8;
        strat[0][2] = 8;
        strat[0][3] = 6;
        strat[0][4] = 6;
        strat[0][5] = 8;
        strat[0][6] = -8;
        strat[0][7] = 99;

        strat[1][0] = -8;
        strat[1][1] = -24;
        strat[1][2] = -4;
        strat[1][3] = -3;
        strat[1][4] = -3;
        strat[1][5] = -4;
        strat[1][6] = -24;
        strat[1][7] = -8;

        strat[2][0] = 8;
        strat[2][1] = -4;
        strat[2][2] = 7;
        strat[2][3] = 4;
        strat[2][4] = 4;
        strat[2][5] = 7;
        strat[2][6] = -4;
        strat[2][7] = 8;

        strat[3][0] = 6;
        strat[3][1] = -3;
        strat[3][2] = 4;
        strat[3][3] = 0;
        strat[3][4] = 0;
        strat[3][5] = 4;
        strat[3][6] = -3;
        strat[3][7] = 6;

        strat[4][0] = 6;
        strat[4][1] = -3;
        strat[4][2] = 4;
        strat[4][3] = 0;
        strat[4][4] = 0;
        strat[4][5] = 4;
        strat[4][6] = -3;
        strat[4][7] = 6;

        strat[5][0] = 8;
        strat[5][1] = -4;
        strat[5][2] = 7;
        strat[5][3] = 4;
        strat[5][4] = 4;
        strat[5][5] = 7;
        strat[5][6] = -4;
        strat[5][7] = 8;

        strat[6][0] = -8;
        strat[6][1] = -24;
        strat[6][2] = -4;
        strat[6][3] = -3;
        strat[6][4] = -3;
        strat[6][5] = -4;
        strat[6][6] = -24;
        strat[6][7] = -8;

        strat[7][0] = 99;
        strat[7][1] = -8;
        strat[7][2] = 8;
        strat[7][3] = 6;
        strat[7][4] = 6;
        strat[7][5] = 8;
        strat[7][6] = -8;
        strat[7][7] = 99;

        return strat;
    }
}
