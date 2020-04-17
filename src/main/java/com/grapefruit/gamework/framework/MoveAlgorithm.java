package com.grapefruit.gamework.framework;

/**
 * The interface Minimax.
 */
public interface MoveAlgorithm {
    /**
     * Calculate best move available with the current state of the game.
     *
     * @param board     the board
     * @param player    the player
     * @param opponent  the opponent
     * @param turnCount the turn count
     * @return the best available Tile
     */
    Tile calculateBestMove(Board board, Player player, Player opponent, int turnCount);

    /**
     * Start timeout. Starts a timeout counter which has int timeout as timer.
     *
     * @param timeout the timeout
     */
    void startTimeout(int timeout);

    /**
     * Destroy. Destroys all threads working in the algorithm.
     */
    void destroy();

    /**
     * Sets depth for the minimax algorithm.
     *
     * @param depth the depth
     */
    void setDepth(int depth);

    /**
     * Sets complexity for the minimax algorithm.
     * So complexity 1 is for example depth 1 with no dynamic/iterative depth.
     * Complexity 5 is for example depth 8 with dynamic/iterative depth
     *
     * @param complexity the complexity
     */
    void setComplexity(int complexity);
}
