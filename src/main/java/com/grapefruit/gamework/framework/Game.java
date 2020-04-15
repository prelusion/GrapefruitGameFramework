package com.grapefruit.gamework.framework;

import javafx.beans.property.SimpleIntegerProperty;

import java.util.List;

/**
 * The type Game.
 */
public abstract class Game {

    private Player[] players;
    private int currentPlayerIndex = 0;
    private int turnTimeout;
    private SimpleIntegerProperty turnTime = new SimpleIntegerProperty();
    private Thread turnTimeThread;
    private Board board;
    private Player currentPlayer;
    private Minimax minimaxAlgorithm;
    /**
     * The Turn count.
     */
    public int turnCount = 1;


    /**
     * Instantiates a new Game.
     *
     * @param board            the board
     * @param players          the players
     * @param minimaxAlgorithm the minimax algorithm
     * @param turnTimeout      the turn timeout
     */
    public Game(Board board, Player[] players, Minimax minimaxAlgorithm, int turnTimeout) {
        this.board = board;
        this.players = players;
        this.turnTimeout = turnTimeout;
        this.currentPlayer = players[0];
        this.minimaxAlgorithm = minimaxAlgorithm;
    }

    /**
     * Sets turn timeout.
     *
     * @param seconds the seconds
     */
    public void setTurnTimeout(int seconds) {
        turnTimeout = seconds;
    }

    /**
     * Start turn timer.
     */
    public void startTurnTimer() {
        resetTurnTimer();

        turnTimeThread = new Thread(() -> {
            while (turnTimeThread != null && !turnTimeThread.isInterrupted() && turnTime.get() >= 0) {
                if (turnTimeThread == null) {
                    break;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    break;
                }
                turnTime.set(turnTime.get() - 1);
            }
        });

        turnTimeThread.start();
    }

    /**
     * Stop turn timer.
     */
    public void stopTurnTimer() {
        if (turnTimeThread != null) {
            turnTimeThread.interrupt();
        }
    }


    /**
     * Gets minimax algorithm.
     *
     * @return the minimax algorithm
     */
    public Minimax getMinimaxAlgorithm() {
        return minimaxAlgorithm;
    }

    /**
     * Gets turn count.
     *
     * @return the turn count
     */
    public int getTurnCount() {
        return turnCount;
    }

    /**
     * Gets turn seconds left.
     *
     * @return the turn seconds left
     */
    public int getTurnSecondsLeft() {
        return turnTime.get();
    }

    /**
     * Reset turn timer.
     */
    public void resetTurnTimer() {
        stopTurnTimer();
        turnTime.set(turnTimeout);
    }

    /**
     * Gets turn timeout.
     *
     * @return the turn timeout
     */
    public int getTurnTimeout() {
        return turnTimeout;
    }

    /**
     * Gets turn time property.
     *
     * @return the turn time property
     */
    public SimpleIntegerProperty getTurnTimeProperty() {
        return turnTime;
    }

    /**
     * Get players player [ ].
     *
     * @return the player [ ]
     */
    public Player[] getPlayers() {
        return players;
    }

    /**
     * Next player is done in RR fashion, starting from index 0.
     * If first player needs to be selected randomly, player array in constructor needs to be sorted randomly.
     */
    public void nextPlayer() {
        currentPlayerIndex++;
        if (currentPlayerIndex == players.length) currentPlayerIndex = 0;
        currentPlayer = players[currentPlayerIndex];
    }

    /**
     * Gets opponent player.
     *
     * @return the opponent player
     */
    public Player getOpponentPlayer() {
        int index = currentPlayerIndex + 1;
        if (index == players.length) index = 0;
        return players[index];
    }

    /**
     * Gets current player.
     *
     * @return the current player
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Sets current player.
     *
     * @param player the player
     */
    public void setCurrentPlayer(Player player) {
        currentPlayer = player;
        for (int i = 0; i < players.length; i++) {
            if (players[i] == player) currentPlayerIndex = i;
        }
    }

    /**
     * Gets player by name.
     *
     * @param name the name
     * @return the player by name
     */
    public Player getPlayerByName(String name) {
        for (Player player : players) {
            if (player.getName().equals(name)) return player;
        }
        return null;
    }

    /**
     * Gets board.
     *
     * @return Board, the games board.
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Is valid move boolean.
     *
     * @param row    the row
     * @param col    the col
     * @param player the player
     * @return the boolean
     */
    public abstract boolean isValidMove(int row, int col, Player player);

    /**
     * Play move boolean.
     *
     * @param row    the row
     * @param col    the col
     * @param player the player
     * @return the boolean
     */
    public abstract boolean playMove(int row, int col, Player player);

    /**
     * This function will check if the game should be ended or not.
     *
     * @return boolean, State of the game if its ended or not.
     */
    public abstract boolean hasFinished();

    /**
     * Gets winner.
     *
     * @return the winner
     */
    public abstract Player getWinner();

    /**
     * Has winner boolean.
     *
     * @return boolean, true if a winner is found
     */
    public boolean hasWinner() {
        return hasFinished() && getWinner() != null;
    }

    /**
     * Is tie boolean.
     *
     * @return the boolean
     */
    public boolean isTie() {
        return hasFinished() && getWinner() == null;
    }

    /**
     * Gets available moves.
     *
     * @param player the player
     * @return the available moves
     */
    public List<Tile> getAvailableMoves(Player player) {
        return getBoard().getAvailableMoves(player);
    }

    /**
     * Destroy.
     */
    public void destroy() {
        resetTurnTimer();
    }
}
