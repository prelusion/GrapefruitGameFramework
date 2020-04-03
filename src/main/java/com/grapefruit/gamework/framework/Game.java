package com.grapefruit.gamework.framework;


import java.util.List;

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

    private Player winner;

    private Player virtualWinner;

    protected boolean finished = false;

    public Game(Board board, Player[] players, int turnTimeout) {
        this.board = board;
        this.players = players;
        this.turnTimeout = turnTimeout;
        this.currentPlayer = players[0];
        nextPlayer();
    }

    public Player[] getPlayers() {
        return players;
    }

    /**
     * Next player is done in RR fashion, starting from index 0.
     * If first player needs to be selected randomly, player array in constructor needs to be sorted randomly.
     */
    public void nextPlayer() {
        currentPlayer = players[currentPlayerIndex];
        currentPlayerIndex++;
        if (currentPlayerIndex == players.length) currentPlayerIndex = 0;
    }

    public Player getOpponentPlayer() {
        int index = currentPlayerIndex + 1;
        if (index == players.length) index = 0;
        return players[index];
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

    /**
     * This function will check if the given move is a valid move on the board.
     *
     * @param player, player is given to check which side is playing.
     * @param tile,   Looks of the given tile is valid.
     * @return boolean, State of the move is valid or not.
     */
    public abstract boolean isValidMove(int row, int col, Player player);

    /**
     * @param row
     * @param col
     */
    public boolean playMove(int row, int col, Player player) {
        if (!isValidMove(row, col, player)) {
            return false;
        }

        board.setMove(row, col, player);

        if (hasFinished()) {
            finished = true;
        }

        return true;
    }

    /**
     * This function will check if the game should be ended or not.
     *
     * @return boolean, State of the game if its ended or not.
     */
    public abstract boolean hasFinished();

    /**
     * This function will check if the game should be ended or not.
     *
     * @return boolean, State of the game if its ended or not.
     */
    public abstract boolean hasFinished(Board board);

    /**
     * Checks whether the game has a WINNER, a TIE or NONE of those.
     *
     * @return GameResult, The result of the game so WINNER, TIE or NONE
     */
    public abstract GameResult getGameResult();

    /**
     * Checks whether the game has a WINNER, a TIE or NONE of those.
     *
     * @return GameResult, The result of the game so WINNER, TIE or NONE
     */
    public abstract GameResult getGameResult(Board board);

    /**
     * @return boolean, true if a winner is found
     */
    public boolean hasWinner() {
        return winner != null;
    }

    public boolean isTie() {
        return getGameResult() == GameResult.TIE;
    }

    /**
     * @return Player, Get the player if there is a winner. If the game has finished an
     */
    public Player getWinner() {
        return winner;
    }

    protected void setWinner(Player player) {
        winner = player;
    }


    /**
     * @return Player, Get the player if there is a winner. If the game has finished an
     */
    public Player getVirtualWinner() {
        return virtualWinner;
    }

    protected void setVirtualWinner(Player player) {
        virtualWinner = player;
    }


    /**
     * Uses isValidMove() To check whether moves are available.
     *
     * @param player, searches available moves for that specific player.
     * @return Move[] of available moves for the given player.
     */  //TODO
    public abstract List<Tile> getAvailableMoves(Player player);

    public enum GameResult {
        WINNER,
        TIE,
        NONE
    }
}
