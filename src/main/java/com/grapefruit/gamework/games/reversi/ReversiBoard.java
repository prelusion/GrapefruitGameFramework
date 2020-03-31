package com.grapefruit.gamework.games.reversi;

import com.grapefruit.gamework.framework.Board;
import com.grapefruit.gamework.framework.Player;
import com.grapefruit.gamework.framework.Tile;

import java.util.ArrayList;
import java.util.List;

public class ReversiBoard extends Board {
    /**
     * Constructor for making a new Board object.
     *
     * @param boardSize
     */
    public ReversiBoard(int boardSize) {
        super(boardSize);
    }

    @Override
    public void getAvailableMoves(Player player) {
        for (int x = 0; x < grid.length - 1; x++) {
            for (int y = 0; y < grid.length - 1; y++) {
                if (grid[x][y].getPlayer() == player){
                    System.out.println("Position: " + x +  " " + y);
                    List<List<Integer>> validPositions = getAvailablePositions(x, y, player);
                    if (validPositions.size() < 1) {
                        System.out.println("No valid position found");
                    } else {
                        for (List<Integer> pos : validPositions) {
                            System.out.println("Valid position: " + pos.get(0) + " " + pos.get(1));
                        }
                    }

                }
            }
        }
    }

    private List<List<Integer>> getAvailablePositions(int row, int col, Player player) {
        List<List<Integer>> positions = new ArrayList<>();

        List<Integer> position = checkPosition(row - 1, col, player, true);
        if (position != null) positions.add(position);

        return positions;
    }

    private List<Integer> checkPosition(int row, int col, Player player, boolean first) {
        if (row < 0 || row > boardSize || col < 0 || col > boardSize) {
            return null;
        }

        Tile tile = grid[row][col];
        Player positionPlayer = tile.getPlayer();

        if (positionPlayer == player) {
            return null;
        } else if (!first && positionPlayer == null) {
            ArrayList<Integer> position = new ArrayList<>();
            position.add(row);
            position.add(col);
            return position;
        } else if (positionPlayer != null){
            return checkPosition(row - 1, col, player, false);
        } else {
            return null;
        }
    }

    static class Position {
        int x;
        int y;

        Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
