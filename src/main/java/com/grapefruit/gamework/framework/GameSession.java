package com.grapefruit.gamework.framework;

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

    /**
     * The rules and conditions for the chosen game.
     */
    private MoveSetter moveSetter;

    /**
     * Constructor of making an GameSession
     * @param moveSetter .
     * @param players .
     * @param turnTimeout, gives a timeout limit for turns.
     * @param boardSize, gives a grid size for the new board.
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

    /**
     * @param move, move is given to set the move on the board and apply all necessary changes.
     */
    public void setMove(Move move) {
        moveSetter.setMove(this, move);
    }

    /**
     * This function will check if the given move is a valid move on the board.
     * @param player, player is given to check which side is playing.
     * @param move, Looks of the given move is valid.
     * @return boolean, State of the move is valid or not.
     */
    public boolean isValidMove(Player player, Move move) {
        return moveSetter.isValidMove(this, move);
    }

    /**
     * This function will check if the game should be ended or not.
     * @return boolean, State of the game if its ended or not.
     */
    public boolean isGameEnded() { return moveSetter.isGameEnded(this); };

    /**
     * @return boolean, true if a winner is found.
     */
    public Result isWinner() { return moveSetter.isWinner(this, players); }

    /**
     * @return Player, Get the player if there is a winner.
     */
    public Player getWinner() { return moveSetter.getWinner(this, players); }

    /**
     * This function will check who has won the game (isEndCondition() should be called first).
     * @return String, returns string of a player name when player has won. Returns "remise" if its a tie and returns an empty string if nowone won and game hasnt ended.
     */
    public GameState getGameState() { return moveSetter.getGameState(this, players); };


    /**
     * Uses isValidMove() To check whether moves are available.
     * @param player, searches available moves for that specific player.
     * @return Move[] of available moves for the given player.
     */  //TODO
    public List<Move> getCurrentAvailableMoves(Player player) {
        return null;
    }

    /**
     * Uses isValidMove() To check whether moves are available.
     * @param player, searches available moves for that specific player.
     * @return Move[] of available moves for the given player.
     */  //TODO
    public List<Move> getMoveAvailableMoves(Player player, Move move) {
        return null;
    }

    public Move createMove(int row, int col, Player player) {
        return new Move(new Tile(row, col, 1), player);
    }
}
