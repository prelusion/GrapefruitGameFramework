package com.grapefruit.gamework.framework.template;

import com.grapefruit.gamework.framework.old.Move;

import java.util.List;

public abstract class Game {
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

    /**
     * Constructor of making an GameSession
     *
     * @param int turnTimeout, gives a timeout limit for turns.
     * @param int boardSize, gives a grid size for the new board.
     */
    public Game(Board board, Player[] players, int turnTimeout) {
        this.board = board;
        this.players = players;
        this.turnTimeout = turnTimeout;
    }

    /**
     * @return Arraylist<Player> of players.
     */
    public Player[] getPlayers() {
        return players;
    }

    public Player getNextPlayer() {
        return null;
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

    public Move createMove(int row, int col, Player player) {
        return new Move(new Tile(row, col, 1), player);
    }

    /**
     * This function will check if the given move is a valid move on the board.
     *
     * @param player, player is given to check which side is playing.
     * @param move,   Looks of the given move is valid.
     * @return boolean, State of the move is valid or not.
     */
    public abstract boolean isValidMove(Player player, Tile tile);

    /**
     * @param move, move is given to set the move on the board and apply all necessary changes.
     */
    public boolean setMove(Player player, Tile tile) {
        if (!isValidMove(player, tile)) {
            return false;
        }

        board.setPiece(tile);

        return true;
    }

    /**
     * This function will check if the game should be ended or not.
     *
     * @return boolean, State of the game if its ended or not.
     */
    public abstract boolean hasGameFinished();

    /**
     * @return boolean, true if a winner is found
     */
    public abstract boolean hasWinner();

    public abstract boolean isTie();

    /**
     * @return Player, Get the player if there is a winner. If the game has finished an
     */
    public abstract Player getWinner();

    /**
     * Uses isValidMove() To check whether moves are available.
     *
     * @param player, searches available moves for that specific player.
     * @return Move[] of available moves for the given player.
     */  //TODO
    public abstract List<Tile> getAvailableMoves(Player player);
}
