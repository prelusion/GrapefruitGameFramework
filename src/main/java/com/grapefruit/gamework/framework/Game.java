package com.grapefruit.gamework.framework;

import java.util.List;
import java.util.concurrent.ExecutorService;

import javafx.beans.property.MapProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

import java.util.HashMap;
import java.util.Map;

public abstract class Game {

    private Player[] players;
    private int currentPlayerIndex = 0;
    private int turnTimeout;
    private SimpleIntegerProperty turnTime = new SimpleIntegerProperty();
    private Thread turnTimeThread;
    private Board board;
    private Player currentPlayer;


    public Game(Board board, Player[] players, int turnTimeout) {
        this.board = board;
        this.players = players;
        this.turnTimeout = turnTimeout;
        this.currentPlayer = players[0];
    }

    public void startTurnTimer() {
        turnTime.set(turnTimeout);

        turnTimeThread = new Thread(() -> {
            while (!turnTimeThread.isInterrupted()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    break;
                }
                turnTime.set(turnTime.get() - 1);
            }

            turnTimeThread = null;
        });

        turnTimeThread.start();
    }

    public void stopTurnTimer() {
        if (turnTimeThread != null) {
            turnTimeThread.interrupt();
        }
        turnTimeThread = null;
    }

    public int getTurnSecondsLeft() {
        return turnTime.get();
    }

    public void resetTurnTimer() {
        turnTime.set(turnTimeout);
    }

    public SimpleIntegerProperty getTurnTimeProperty() {
        return turnTime;
    }

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

    public Player getOpponentPlayer() {
        int index = currentPlayerIndex + 1;
        if (index == players.length) index = 0;
        return players[index];
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player player) {
        currentPlayer = player;
        for (int i = 0; i < players.length; i++) {
            if (players[i] == player) currentPlayerIndex = i;
        }
    }

    public Player getPlayerByName(String name) {
        for (Player player : players) {
            if (player.getName().equals(name)) return player;
        }
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


    public abstract boolean isValidMove(int row, int col, Player player);

    public abstract boolean playMove(int row, int col, Player player);

    /**
     * This function will check if the game should be ended or not.
     *
     * @return boolean, State of the game if its ended or not.
     */
    public abstract boolean hasFinished();

    public abstract Player getWinner();

    /**
     * @return boolean, true if a winner is found
     */
    public boolean hasWinner() {
        return hasFinished() && getWinner() != null;
    }

    public boolean isTie() {
        return hasFinished() && getWinner() == null;
    }

    public List<Tile> getAvailableMoves(Player player) {
        return getBoard().getAvailableMoves(player);
    }
}
