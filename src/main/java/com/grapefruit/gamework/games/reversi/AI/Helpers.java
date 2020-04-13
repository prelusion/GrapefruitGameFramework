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
        }
        return hasEdge ? 35 : 0;
    }

    public static int betweenCornerPoints(ReversiBoard board, Tile move) {
        String side = null;
        if (move.getRow() == board.top) {
            side = "Top";
        } else if (move.getRow() == board.bottom) {
            side = "Down";
        } else if (move.getCol() == board.left) {
            side = "Left";
        } else if (move.getCol() == board.right) {
            side = "Right";
        }

        if(side == null) {
            return 0;
        }

        boolean hasEdge = true;
        switch (side) {
            case "Top":
                if(board.getPlayer(board.top, board.left) != null && board.getPlayer(board.top, board.right) != null) {
                    for (int i = board.left; i < board.right; i++) {
                        if (board.getPlayer(board.top, i) == null) {
                            hasEdge = false;
                        }
                    }
                } else {
                    hasEdge = false;
                }
                break;
            case "Right":
                if(board.getPlayer(board.top, board.right) != null && board.getPlayer(board.bottom, board.right) != null) {
                    for (int i = board.top; i < board.bottom; i++) {
                        if (board.getPlayer(i, board.right) == null) {
                            hasEdge = false;
                        }
                    }
                } else {
                    hasEdge = false;
                }
                break;
            case "Down":
                if(board.getPlayer(board.bottom, board.left) != null && board.getPlayer(board.bottom, board.right) != null) {
                    for (int i = board.left; i < board.right; i++) {
                        if (board.getPlayer(board.bottom, i) == null) {
                            hasEdge = false;
                        }
                    }
                } else {
                    hasEdge = false;
                }
                break;
            case "Left":
                if(board.getPlayer(board.top, board.left) != null && board.getPlayer(board.bottom, board.left) != null) {
                    for (int i = board.top; i < board.bottom; i++) {
                        if (board.getPlayer(i, board.left) == null) {
                            hasEdge = false;
                        }
                    }
                } else {
                    hasEdge = false;
                }
                break;

        }

        if(hasEdge) {
            return 35;
        }
        return 0;
    }


    public static int adjacentValue(ReversiBoard board, Tile move, Player opponent) {
        String side = null;
        if (move.getRow() == board.top) {
            side = "Top";
        } else if (move.getRow() == board.bottom) {
            side = "Down";
        } else if (move.getCol() == board.left) {
            side = "Left";
        } else if (move.getCol() == board.right) {
            side = "Right";
        }

        if(side == null) {
            return 0;
        }

        boolean isGoodPosition = false;
        if(!isCorner(board, move)) {
            switch (side) {
                case "Top":
                    if (checkAdjecent(board, move.getRow(), move.getCol(), board.top, -1, opponent) && checkAdjecent(board, move.getRow(), move.getCol(), board.top,  1, opponent)) {
                        isGoodPosition = true;
                    }
                    break;
                case "Right":
                    if (checkAdjecent(board, move.getRow(), move.getCol(), -1, board.right, opponent) && checkAdjecent(board, move.getRow(), move.getCol(), 1,  board.right, opponent)) {
                        isGoodPosition = true;
                    }
                    break;
                case "Down":
                    if (checkAdjecent(board, move.getRow(), move.getCol(), board.bottom, -1, opponent) && checkAdjecent(board, move.getRow(), move.getCol(), board.bottom,  1, opponent)) {
                        isGoodPosition = true;
                    }
                    break;
                case "Left":
                    if (checkAdjecent(board, move.getRow(), move.getCol(), -1, board.left, opponent) && checkAdjecent(board, move.getRow(), move.getCol(), 1,  board.left, opponent)) {
                        isGoodPosition = true;
                    }
                    break;
            }
        }
        if (isGoodPosition) {
            return 20;
        }
        return 0;
    }

    public static boolean checkAdjecent(Board board, int row, int col, int dRow, int dCol, Player opponent) {
        if (board.getPlayer(row, col) != opponent && !isCorner(board, board.getTile(row, col))) {
            return checkAdjecent(board, row + dRow, col + dCol, dRow, dCol, opponent);
        } else if (board.getPlayer(row, col) == opponent) {
            return true;
        } else {
            return false;
        }
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
