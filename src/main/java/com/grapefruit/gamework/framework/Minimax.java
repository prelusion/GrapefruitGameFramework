package com.grapefruit.gamework.framework;

/**
 * The interface Minimax.
 */
public interface Minimax {
    /**
     * Calculate best move tile.
     *
     * @param board     the board
     * @param player    the player
     * @param opponent  the opponent
     * @param turnCount the turn count
     * @return the tile
     */
    Tile calculateBestMove(Board board, Player player, Player opponent, int turnCount);

    /**
     * Start timeout.
     *
     * @param timeout the timeout
     */
    void startTimeout(int timeout);

    /**
     * Destroy.
     */
    void destroy();

    /**
     * Sets depth.
     *
     * @param depth the depth
     */
    void setDepth(int depth);

    /**
     * Sets complexity.
     *
     * @param complexity the complexity
     */
    void setComplexity(int complexity);
}
