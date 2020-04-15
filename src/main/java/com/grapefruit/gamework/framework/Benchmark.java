package com.grapefruit.gamework.framework;

/**
 * The interface Benchmark.
 */
public interface Benchmark {
    /**
     * Calculate depth int.
     *
     * @param depth the depth
     * @return the int
     */
    public int calculateDepth(int depth);

    /**
     * Init worst case board.
     */
    public void initWorstCaseBoard();
}
