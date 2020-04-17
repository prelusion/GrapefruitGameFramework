package com.grapefruit.gamework.app.util;

import java.util.Random;

/**
 * The type Global random.
 */
public class GlobalRandom {
    private static Random random = new Random();

    /**
     * Gets random generator.
     *
     * @return the random generator
     */
    public static Random getRandomGenerator() {
        return random;
    }
}
