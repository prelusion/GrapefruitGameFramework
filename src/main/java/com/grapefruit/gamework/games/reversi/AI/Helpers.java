package com.grapefruit.gamework.games.reversi.AI;

import com.grapefruit.gamework.framework.Board;
import com.grapefruit.gamework.framework.Player;
import com.grapefruit.gamework.framework.Tile;
import com.grapefruit.gamework.games.reversi.Reversi;
import com.grapefruit.gamework.games.reversi.ReversiBoard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Helpers {


    public static int comboPoints(ReversiBoard board, Tile move, Player player) {
        boolean hasEdge = true;

        if (isEdge(board, move)) {
            if (move.getRow() == board.top) {
                for (int i = 0; i < board.getBoardSize() - 1; i++) {
                    if (board.getPlayer(board.top, i) != player) {
                        hasEdge = false;
                    }
                }
            } else if (move.getRow() == board.bottom) {
                for (int i = 0; i < board.getBoardSize() - 1; i++) {
                    if (board.getPlayer(board.bottom, i) != player) {
                        hasEdge = false;
                    }
                }
            } else if (move.getCol() == board.left) {
                for (int i = 0; i < board.getBoardSize() - 1; i++) {
                    if (board.getPlayer(i, board.left) != player) {
                        hasEdge = false;
                    }
                }
            } else if (move.getCol() == board.right) {
                for (int i = 0; i < board.getBoardSize() - 1; i++) {
                    if (board.getPlayer(i, board.right) != player) {
                        hasEdge = false;
                    }
                }
            }
            if (hasEdge) System.out.println("Returning combo points");
        }

        return hasEdge ? 999 : 0;
    }

    public static int betweenCornerPoints(ReversiBoard board, Tile move, Player player) {
        String side = "Top";

        switch (side) {
            case "Top":
                for (int i = board.top; i < board.getBoardSize(); i++) {
                    Tile tile = board.getTile(move.getRow() - i, move.getCol());
                }
                break;
            case "Right":
                for (int i = 0; i < board.getBoardSize(); i--) {
                    Tile tile = board.getTile(i, board.right);
                }
                break;
            case "Down":
                for (int i = 0; i < board.getBoardSize(); i++) {
                    Tile tile = board.getTile(tile1.getRow() + i, tile1.getCol()).setStrategicValue(value);
                }
                break;
            case "Left":
                for (int i = 0; i < getBoardSize(); i++) {
                    getTile(tile1.getRow(), tile1.getCol() - i).setStrategicValue(value);
                }
                break;
        }
        return 0;
    }

    private static boolean isCorner(Board board, Tile tile) {
        if (tile.getRow() == 0 && tile.getCol() == 0) {
            return true;
        } else if (tile.getRow() == 0 && tile.getCol() == board.getBoardSize() - 1) {
            return true;
        } else if (tile.getRow() == board.getBoardSize() - 1 && tile.getCol() == 0) {
            return true;
        } else if (tile.getRow() == board.getBoardSize() - 1 && tile.getCol() == board.getBoardSize() - 1) {
            return true;
        }
        return false;
    }

    public static boolean isEdge(ReversiBoard board, Tile tile) {
        return tile.getRow() == board.top || tile.getRow() == board.bottom ||
                tile.getCol() == board.left || tile.getCol() == board.right;
    }

    private static Map<String, List<Tile>> getEdges(Board board, List<Tile> tiles) {
        List<Tile> topEdge = new ArrayList<>();
        List<Tile> bottomEdge = new ArrayList<>();
        List<Tile> leftEdge = new ArrayList<>();
        List<Tile> rightEdge = new ArrayList<>();

        for (Tile tile : tiles) {
            if (tile.getRow() == 0) {
                topEdge.add(tile);
            } else if (tile.getRow() == board.getBoardSize() - 1) {
                bottomEdge.add(tile);
            } else if (tile.getCol() == 0) {
                leftEdge.add(tile);
            }  else if (tile.getCol() == board.getBoardSize() - 1) {
                rightEdge.add(tile);
            }
        }

        Map<String, List<Tile>> map = new HashMap<String, List<Tile>>();
        map.put("top", topEdge);
        map.put("bottom", bottomEdge);
        map.put("left", leftEdge);
        map.put("right", rightEdge);

        return map;
    }

}
