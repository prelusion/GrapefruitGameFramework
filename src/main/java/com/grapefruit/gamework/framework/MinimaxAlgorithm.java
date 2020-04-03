package com.grapefruit.gamework.framework;

import com.grapefruit.gamework.games.reversi.ReversiBoard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.max;
import static java.lang.Integer.min;

public class MinimaxAlgorithm {
    private Game game;
    private Player player;
    private Player opponent;
    public MinimaxAlgorithm(Game game) {
        this.game = game;
    }

    public Tile calculateBestMove(Board board, Player player, Player opponent, int depth) {
        this.player = player;
        this.opponent = opponent;
        int alpha = -999999;
        int beta = +999999;
        int maxScore = -9999999;
        Tile bestTile = null;

        List<Tile> moves = board.getAvailableMoves(player);
        Map<Tile, Integer> tiles = new HashMap<>();
        for (Tile tile : moves) {
            Board newBoard = new ReversiBoard(board.boardSize);
            newBoard.copyState(board);
            newBoard.setMove(tile.getRow(), tile.getCol(), player);

            tiles.put(tile, minimax(
                    depth - 1,
                    newBoard,
                    tile.getStrategicValue(),
                    alpha,
                    beta,
                    false
            ));
        }

        int bestScore = -9999;
        for (Map.Entry<Tile, Integer> entry : tiles.entrySet()) {
            if (entry.getValue() > bestScore) {
                System.out.println(String.format("new best move: %s,%s - strategic value: %s, score: %s", entry.getKey().getRow(), entry.getKey().getCol(), entry.getKey().getStrategicValue(), entry.getValue()));
                bestTile = entry.getKey();
                bestScore = entry.getValue();
            } else {
                System.out.println(String.format("not best move: %s,%s - strategic value: %s, score: %s", entry.getKey().getRow(), entry.getKey().getCol(), entry.getKey().getStrategicValue(), entry.getValue()));
            }
        }

        if (bestTile != null) {
            System.out.println(String.format("best move: %s,%s - strategic value: %s, score: %s", bestTile.getRow(), bestTile.getCol(), bestTile.getStrategicValue(), bestScore));
        }

        return bestTile;
    }


    public int minimax(int depth, Board board, int score, int alpha, int beta, boolean maximizingPlayer) {
        if (depth == 0) {
            return score;
        }

        if (maximizingPlayer) {
            int maxScore = -999;
            List<Tile> moves = board.getAvailableMoves(player);

            if (moves.size() == 0) {
                System.out.println("hoi");
                if(board.hasFinished(new Player[]{player, opponent})) {
                    System.out.println("test2");
                    System.out.println("test2");
                    if(game.getGameResult() == Game.GameResult.WINNER) {
                        System.out.println("WINNNERERERRR");
                        System.out.println("WINNNERERERRR");
                        System.out.println("WINNNERERERRR");
                        System.out.println("WINNNERERERRR");
                        if(game.getVirtualWinner() == player) {
                           return 9999;
                        }
                        return -9999;
                    }
                }
                return score;
            }

            for (Tile move : moves) {
                Board newBoard = new ReversiBoard(board.boardSize);
                newBoard.copyState(board);
                newBoard.setMove(move.getRow(), move.getCol(), player);

                int currentScore = minimax(
                        depth - 1,
                        newBoard,
                        score + move.getStrategicValue(),
                        alpha, beta,
                        false
                );

                maxScore = max(maxScore, currentScore);

                alpha = max(alpha, currentScore);
                if (beta <= alpha) {
                    break;
                }

                if(move.getStrategicValue() == 99) {
                    break;
                }
            }
            return maxScore;

        } else {
            int minScore = 999;
            List<Tile> moves = board.getAvailableMoves(opponent);

            if (moves.size() == 0) {
                if(game.hasFinished(board)) {
                    if(game.getGameResult() == Game.GameResult.WINNER) {
                        System.out.println("WINNNERERERRR");
                        System.out.println("WINNNERERERRR");
                        System.out.println("WINNNERERERRR");
                        System.out.println("WINNNERERERRR");
                        if(game.getVirtualWinner() == player) {
                            return -9999;
                        }
                        return 9999;
                    }
                }
                return score;
            }

            for (Tile move : moves) {
                Board newBoard = new ReversiBoard(board.boardSize);
                newBoard.copyState(board);
                newBoard.setMove(move.getRow(), move.getCol(), opponent);

                int currentScore = minimax(
                        depth - 1,
                        newBoard,
                        score - move.getStrategicValue(),
                        alpha,
                        beta,
                        true
                );

                minScore = min(minScore, currentScore);

                beta = min(beta, currentScore);
                if (beta <= alpha) {
                    break;
                }
                if(move.getStrategicValue() == 99) {
                    break;
                }
            }
            return minScore;
        }
    }
}
