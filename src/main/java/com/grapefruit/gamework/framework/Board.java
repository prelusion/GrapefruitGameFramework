package com.grapefruit.gamework.framework;

import java.util.List;

public class Board {

    /**
     * This grid is the board. Size is dynamic so instantiated in the constructor.
     */
    private Tile[][] grid;

    /**
     * Constructor for making a new Board object.
     * @param int, boardSize is given to give the board grid a size.
     */
    public Board(int boardSize) {
        grid = new Tile[boardSize][boardSize];
        createBoard();
    }

    /**
     * Creates a board of given size with strategicValues given from the game implmentation. (TODO)
     */
    public void createBoard() {
        for (int x = 0; x < grid.length-1; x++) {
            for (int y = 0; y < grid.length-1; y++) {
                grid[x][y] = new Tile(x, y, /* TODO  */ 0);
            }
        }
    }

    /**
     * @param Move, move is given to set the move on the board and apply all necessary changes.
     */
     public void setMove(Move move) {
        //
     }
        //TODO Set the move with the given function of the Rules implementation


    /**
     * This function will check if the given move is a valid move on the board.
     * @param Player, player is given to check which side is playing.
     * @param Move, Looks of the given move is valid.
     */
    public boolean isValidMove(Move move) {
        //TODO check if the move is a valid move with the help of the Rule implementation of a certain game.
        return false;
    }

    /**
     * Uses isValidMove() To check whether moves are available.
     * @param Player, searches available moves for that specific player.
     * @return Arraylist<Move> of available moves for the given player.
     */
    public Move[] getAvailableMoves(Player player) {
        return null;
    }


    /**
     * @return Tile[][], Get the current board with tiles.
     */
    public Tile[][] getGrid() {
        return grid;
    }
}
