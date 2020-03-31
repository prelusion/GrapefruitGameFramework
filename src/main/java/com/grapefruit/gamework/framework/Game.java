package com.grapefruit.gamework.framework;


import java.util.HashSet;

public abstract class Game {
    /**
     * List of players on the client side;
     */
    private Player[] players;
    private int currentPlayerIndex = 0;
    /**
     * The time a player has for their turn
     */
    private int turnTimeout = 10;

    /**
     * The board for the chosen game
     */
    private Board board;

    private Player currentPlayer;

    protected boolean finished = false;
    protected GameResult gameResult = GameResult.NONE;

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
        this.currentPlayer = players[0];
    }

    public String getName() {
        return "";
    }

    public String getIcon() {
        return null;
    }

    /**
     * @return Arraylist<Player> of players.
     */
    public Player[] getPlayers() {
        return players;
    }

    /**
     * Next player is done in RR fashion, starting from index 0.
     * If first player needs to be selected randomly, player array in constructor needs to be sorted randomly.
     *
     * @return
     */
    public void nextPlayer() {
        currentPlayer = players[currentPlayerIndex];
        currentPlayerIndex++;
        if (currentPlayerIndex == players.length) currentPlayerIndex = 0;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayerTurn) {
        this.currentPlayer = currentPlayerTurn;
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

    public Tile createMove(int row, int col, Player player) {
        return new Tile(row, col, 1, player);
    }

    /**
     * This function will check if the given move is a valid move on the board.
     *
     * @param player, player is given to check which side is playing.
     * @param tile,   Looks of the given tile is valid.
     * @return boolean, State of the move is valid or not.
     */
    public abstract boolean isValidMove(int row, int col, Player player);

    /**
     * Checks whether the game has a WINNER, a TIE or NONE of those.
     *
     * @return GameResult, The result of the game so WINNER, TIE or NONE
     */
    public abstract void calculateGameResult();

    /**
     * @param row
     * @param col
     */
    public boolean doMove(int row, int col) {
        if (!isValidMove(row, col, currentPlayer)) {
            return false;
        }

        board.setPlayer(row, col, currentPlayer);

        if (hasGameFinished()) {
            finished = true;
        }

        return true;
    }

    public abstract boolean playMove(int row, int col);

    /**
     * This function will check if the game should be ended or not.
     *
     * @return boolean, State of the game if its ended or not.
     */
    public abstract boolean hasGameFinished();

    /**
     * @return boolean, true if a winner is found
     */
    public boolean hasWinner() {
        return getGameResult() == GameResult.WINNER;
    }

    public boolean isTie() {
        return getGameResult() == GameResult.TIE;
    }

    public GameResult getGameResult() {
        return gameResult;
    }

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
    public abstract HashSet<Tile> getAvailableMoves(Player player);

    public enum GameResult {
        WINNER,
        TIE,
        NONE
    }
}
