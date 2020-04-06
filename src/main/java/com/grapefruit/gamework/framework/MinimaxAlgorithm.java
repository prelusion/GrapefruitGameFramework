package com.grapefruit.gamework.framework;

import com.grapefruit.gamework.games.reversi.ReversiBoard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.grapefruit.gamework.games.reversi.ReversiFactory.STRATEGIC_VALUES;
import static java.lang.Integer.*;

public class MinimaxAlgorithm {
    private Player player;
    private Player opponent;
    ArrayList<Thread> threads;

    public MinimaxAlgorithm() {  }

    public Tile calculateBestMove(Board board, Player player, Player opponent, int depth) {
        this.player = player;
        this.opponent = opponent;
        threads = new ArrayList<>();
        int alpha = MIN_VALUE;
        int beta = MAX_VALUE;
        Tile bestTile = null;

        List<Tile> moves = board.getAvailableMoves(player);
        Map<Tile, Integer> tiles = new HashMap<>();
        for (Tile tile : moves) {
            Board newBoard = new ReversiBoard(board.BOARDSIZE, STRATEGIC_VALUES);
            newBoard.copyState(board);
            newBoard.setMove(tile.getRow(), tile.getCol(), player);
            threads.add(new Thread(() -> {
                tiles.put(tile, minimax(
                        depth - 1,
                        newBoard,
                        tile.getStrategicValue(),
                        alpha,
                        beta,
                        false
                ));
            }));
            threads.get(threads.size() - 1).start();
        }

        for(Thread thread: threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if(tiles.size() != moves.size()) {
            return null;
        }

        int bestScore = MIN_VALUE;
        for (Map.Entry<Tile, Integer> entry : tiles.entrySet()) {
            if (entry.getValue() > bestScore) {
               // System.out.println(String.format("new best move: %s,%s - strategic value: %s, score: %s", entry.getKey().getRow(), entry.getKey().getCol(), entry.getKey().getStrategicValue(), entry.getValue()));
                bestTile = entry.getKey();
                bestScore = entry.getValue();
            } else {
                //System.out.println(String.format("not best move: %s,%s - strategic value: %s, score: %s", entry.getKey().getRow(), entry.getKey().getCol(), entry.getKey().getStrategicValue(), entry.getValue()));
            }
        }

        if (bestTile != null) {
            //System.out.println(String.format("best move: %s,%s - strategic value: %s, score: %s", bestTile.getRow(), bestTile.getCol(), bestTile.getStrategicValue(), bestScore));
        }

        return bestTile;
    }


    public int minimax(int depth, Board board, int score, int alpha, int beta, boolean maximizingPlayer) {
        if (depth == 0) {
            return score;
        }


        if (maximizingPlayer) {
            int maxScore = MIN_VALUE;
            List<Tile> moves = board.getAvailableMoves(player);

            if (moves.size() == 0) {
                return score;
            }

            for (Tile move : moves) {
                Board newBoard = new ReversiBoard(board.BOARDSIZE, STRATEGIC_VALUES);
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

                if (move.getStrategicValue() == 99) {
                    break;
                }
            }
            return maxScore;

        } else {
            int minScore = MAX_VALUE;
            List<Tile> moves = board.getAvailableMoves(opponent);

            if (moves.size() == 0) {
                return score;
            }

            for (Tile move : moves) {
                Board newBoard = new ReversiBoard(board.BOARDSIZE, STRATEGIC_VALUES);
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
                if (move.getStrategicValue() == 99) {
                    break;
                }
            }
            return minScore;
        }
    }
}
