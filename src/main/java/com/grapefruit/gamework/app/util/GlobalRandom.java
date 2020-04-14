package com.grapefruit.gamework.app.util;

import java.util.Random;

public class GlobalRandom {
    private static Random random = new Random();

    public static Random getRandomGenerator() {
        return random;
    }
}
