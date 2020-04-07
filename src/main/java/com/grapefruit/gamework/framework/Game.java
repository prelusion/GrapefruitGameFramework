package com.grapefruit.gamework.framework;

import java.util.List;
import java.util.concurrent.ExecutorService;

import javafx.beans.property.MapProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

import java.util.HashMap;
import java.util.Map;

public abstract class Game {

    private Player[] players;
    private int currentPlayerIndex = 0;
    private int turnTimeout;
    private Board board;
    private Player currentPlayer;
    public ObservableMap<Player, Integer> scores;

    public Game(Board board, Player[] players, int turnTimeout) {
        this.board = board;
        this.players = players;
        this.turnTimeout = turnTimeout;
        this.currentPlayer = players[0];
        this.scores = FXCollections.observableHashMap();
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


    public abstract boolean isValidMove(int row, int col, Player player);

    public boolean playMove(int row, int col, Player player) {
        if (!isValidMove(row, col, player)) {
            return false;
        }

        board.setMove(row, col, player);
        calculateScores();
        return true;
    }

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

    public abstract void calculateScores();

    public int getScore(Player player){
        return scores.getOrDefault(player, 0);
    }
}
