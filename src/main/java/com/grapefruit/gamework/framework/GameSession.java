package com.grapefruit.gamework.framework;

import java.util.ArrayList;
import java.util.List;

public abstract class GameSession {
    /**
     * List of players on the client side;
     */
    private Player[] players;

    /**
     * The time a player has for their turn
     */
    private int turnTimeout = 10;

    /**
     * The board for the chosen game
     */
    private Board board;

    private MoveSetter moveSetter;

    /**
     * Constructor of making an GameSession
     * @param int turnTimeout, gives a timeout limit for turns.
     * @param int boardSize, gives a grid size for the new board.
     */
    public GameSession(MoveSetter moveSetter, Player[] players, int turnTimeout, int boardSize) {
        this.moveSetter = moveSetter;
        this.players = players;
        this.turnTimeout = turnTimeout;
        this.board = new Board(boardSize);
    }

    /**
     * @abstract This function will start the current GameSession.
     */
    public abstract void start();

    /**
     * @Param Player, gives the available moves for that player.
     * @return Arraylist<Move> of available moves;
     */
    public Move[] getAvailableMoves(Player player) {
        return board.getAvailableMoves(player);
    }

    /**
     * @return Arraylist<Player> of players.
     */
    public Player[] getPlayers() {
        return players;
    }

    /**
     * @return Board, the games board.
     */
    public Board getBoard() {
        return board;
    }

    /**
     * @return int turnTimeout.
     */
    public int getTurnTimeout() {
        return turnTimeout;
    }

    public boolean isValidMove(Move move) {
        return moveSetter.isValidMove(board, move);
    }

    public void setMove(Move move) {
        moveSetter.setMove(board, move);
    }
}
