package com.grapefruit.gamework.tictactoe;

import com.grapefruit.gamework.framework.*;

import java.util.ArrayList;

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
        if(getGameState() == GameState.ENDED) {
            if(Conditions.checkAllAdjacentConditions(session.getBoard())) {
                hasWinner = Result.WINNER;
                return Result.WINNER;
            }
            return Result.TIE;
        }
        return Result.NONE;
    }

    /**
     * Checks which player won the match and returns that player.
     */
    @Override
    public Player getWinner(GameSession session, Player[] players) {
        if (hasWinner != Result.WINNER) {
            isWinner(session, players);
        }
        if (getGameState() == GameState.ENDED && hasWinner == Result.WINNER) {
            Tile tile = Conditions.getTileOfAvailableConditions(session.getBoard());
            if(tile != null) {
                return tile.getPiece().getPlayer();
            }
        }
        return null;
    }
}
