package com.grapefruit.gamework.games.reversi;

import com.grapefruit.gamework.framework.Board;
import com.grapefruit.gamework.framework.player.Player;
import com.grapefruit.gamework.framework.Tile;

import java.util.HashSet;

public class ReversiBoard extends Board {

    public ReversiBoard(int boardSize) {
        super(boardSize);
    }

    @Override
    public HashSet<Tile> getAvailableMoves(Player player) {
        HashSet<Tile> playerOwnedTiles = new HashSet<>();
        HashSet<Tile> validMoves = new HashSet<>();

        for (int i = 0; i < grid.length - 1; i++) {
            for (int j = 0; j < grid.length - 1; j++) {
                Tile tile = grid[i][j];
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

    private Tile getEmptyTileInDirection(Player player, int startRow, int startCol, int dRow, int dCol) {
        int col = startCol + dCol;
        int row = startRow + dRow;

        if (!isValidLocation(row, col)) {
            return null;
        }

        if (!hasPlayer(row, col)) {
            return grid[row][col];
        }

        if (grid[row][col].getPlayer() == player) {
            return null;
        }

        return getEmptyTileInDirection(player, row, col, dRow, dCol);
    }
}
