package com.grapefruit.gamework.games.tictactoe;

import com.grapefruit.gamework.framework.template.*;

public class TTTGameConditions implements GameCondition {
    private boolean endReached;
    private Result hasWinner;

    @Override
    /**
     * Returns true if there is an end condition reached.
     * @return boolean
     */
    public boolean checkEndConditions(GameSession session) {
        Board board = session.getBoard();
        if (board.isBoardFull()) {
            endReached = true;
            return true;
        }
        return false;
    }

    /**
     * Returns String of player name if a player has won. returns "Remise" if no player has won but the game has ended, returns null if the game hasnt ended.
     * Uses getWinner() to check who won.
     * @return boolean
     */
    @Override
    public GameState getGameState() {
        if (endReached) {
            return GameState.ENDED;
        }
        return GameState.RUNNING;
    }


    /**
     * Checks if a player won the match.
     */
    @Override
    public Result isWinner(GameSession session, Player[] players) {
        Board board = session.getBoard();
        String[][] solutions = new String[board.getGrid().length][board.getGrid().length];


        for (int row = 0; row < board.getGrid().length; row++) {
            for (int col = 0; col < board.getGrid().length; col++) {
                solutions[row][col] = board.getPieceString(row, col);
            }
        }

        hasWinner = Result.WINNER;
        return Result.WINNER;
    }

    /**
     * Checks which player won the match and returns that player.
     */
    @Override
    public Player getWinner(GameSession session, Player[] players) {
        if(hasWinner != Result.WINNER) {
            hasWinner = isWinner(session, players);
        }
        if(getGameState() == GameState.ENDED && hasWinner == Result.WINNER) {
            Board board = session.getBoard();
            Tile[][] grid = board.getGrid();

            String[][] solutions = new String[board.getGrid().length][board.getGrid().length];
            //Horizontal check
            for (int x = 0; x < grid.length; x++) {
                for (int y = 0; y < grid.length; y++) {

                }
            }
        }
        return null;
    }
}
