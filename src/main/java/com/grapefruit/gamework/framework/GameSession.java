package com.grapefruit.gamework.framework;

import java.util.ArrayList;
import java.util.List;

public abstract class GameSession {
    /**
     * List of players on the client side;
     */
    private List<Player> players = new List<Player>();

    /**
     * The board for the chosen game
     */
    private Board board;

    /**
     * The rules for the chosen game
     */
    private ArrayList<Rule> rules = new ArrayList<>();

    /**
     * The time a player has for their turn
     */
    private int turnTimeout = 10;

    /**
     * Constructor of making an GameSession
     * @param int turnTimeout, gives a timeout limit for turns.
     * @param int boardSize, gives a grid size for the new board.
     */
    public GameSession(int turnTimeout, int boardSize) {
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
    public ArrayList<Move> getAvailableMoves(Player player) {
        return board.getAvailableMoves(player);
    }

    /**
     * @return Arraylist<Player> of players.
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
    * @Param Player, Adds player to the Arraylist players.
    */
    public void addPlayer(Player player) { this.players.add(player); }

    /**
     * @return Board, the games board.
     */
    public Board getBoard() {
        return board;
    }

    /**
     * @return Arraylist<Rule> of the current games rules.
     */
    public ArrayList<Rule> getRules() {
        return rules;
    }

    /**
     * @return int turnTimeout.
     */
    public int getTurnTimeout() {
        return turnTimeout;
    }
}
