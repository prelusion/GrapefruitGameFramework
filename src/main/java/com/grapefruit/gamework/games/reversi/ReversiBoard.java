package com.grapefruit.gamework.games.reversi;

import com.grapefruit.gamework.framework.Board;
import com.grapefruit.gamework.framework.Player;
import com.grapefruit.gamework.framework.Tile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ReversiBoard extends Board {

    public ReversiBoard(int boardSize, int[][] strategicValues) {
        super(boardSize, strategicValues);
    }

    @Override
    public List<Tile> getAvailableMoves(Player player) {
        HashSet<Tile> playerOwnedTiles = new HashSet<>();
        ArrayList<Tile> validMoves = new ArrayList<>();

        for (Tile[] row : grid) {
            for (Tile tile : row) {
                if (tile.getPlayer() != null && tile.getPlayer() == player) {
                    playerOwnedTiles.add(tile);
                }
            }
        }

        for (Tile tile : playerOwnedTiles) {
            HashSet<Tile> neighbours = getDirectNeighbours(tile);

            for (Tile neighbour : neighbours) {
                if (neighbour.getPlayer() == null || neighbour.getPlayer() == player)
                    continue;

                Tile emptyTile = getEmptyTileInDirection(
                        player,
                        neighbour.getRow(),
                        neighbour.getCol(),
                        neighbour.getRow() - tile.getRow(),
                        neighbour.getCol() - tile.getCol()
                );

                if (emptyTile == null) continue;

                if (!validMoves.contains(emptyTile))
                    validMoves.add(emptyTile);
            }
        }

        return validMoves;
    }

    private HashSet<Tile> getDirectNeighbours(Tile tile) {
        HashSet<Tile> neighbours = new HashSet<>();

        for (int[] coord : relativeNeighborGrid) {
            int row = tile.getRow() + coord[0];
            int col = tile.getCol() + coord[1];

            if (isValidLocation(row, col)) {
                neighbours.add(grid[row][col]);
            }
        }

        return neighbours;
    }

    private Tile getEmptyTileInDirection(Player player, int row, int col, int dRow, int dCol) {
        if (!isValidLocation(row, col)) {
            return null;
        }

        if (!hasPlayer(row, col)) {
            return grid[row][col];
        }

        if (grid[row][col].getPlayer() == player) {
            return null;
        }

        return getEmptyTileInDirection(player, row + dRow, col + dCol, dRow, dCol);
    }

    public void setMove(int row, int col, Player player) {
        Tile tile = grid[row][col];
        tile.setPlayer(player);

        HashSet<Tile> neighbours = getDirectNeighbours(tile);

        for (Tile neighbour : neighbours) {
            if (neighbour.getPlayer() == null || neighbour.getPlayer() == player)
                continue;

            List<Tile> tiles = getTilesToTurn(
                    player,
                    neighbour.getRow(),
                    neighbour.getCol(),
                    neighbour.getRow() - tile.getRow(),
                    neighbour.getCol() - tile.getCol()
            );

            if (tiles == null) {
                continue;
            }

            for (Tile t : tiles) {
                grid[t.getRow()][t.getCol()] = t;
            }
        }
    }

    private List<Tile> getTilesToTurn(Player player, int startRow, int startCol, int dRow, int dCol) {
        return getTilesToTurn(player, startRow, startCol, dRow, dCol, new ArrayList<>());
    }

    private List<Tile> getTilesToTurn(Player player, int row, int col, int dRow, int dCol, List<Tile> searchOrder) {
        if (!isValidLocation(row, col)) {
            return null;
        }

        if (!hasPlayer(row, col)) {
            return null;
        }

        if (grid[row][col].getPlayer() == player) {
            return searchOrder;
        }

        Tile tile = grid[row][col];

        searchOrder.add(new Tile(tile.getRow(), tile.getCol(), tile.getStrategicValue(), player));

        return getTilesToTurn(player, row + dRow, col + dCol, dRow, dCol, searchOrder);
    }
}
