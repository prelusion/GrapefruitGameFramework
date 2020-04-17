package com.grapefruit.gamework.framework;

import java.util.ArrayList;
import java.util.Stack;

/**
 * The type Abstract minimax.
 */
public abstract class Minimax implements MoveAlgorithm {

    /**
     * The Current depth.
     */
    protected int currentDepth;
    /**
     * The Dynamic depth.
     */
    protected boolean dynamicDepth = true;
    /**
     * The Timeout. Gives the amount of time before the timeout is called.
     */
    protected int timeout;
    /**
     * The Start time.
     */
    protected long startTime;
    /**
     * The Timeout thread.
     */
    protected Thread timeoutThread;
    /**
     * The Timed out. A check whether something is timed out.
     */
    protected boolean timedOut;
    /**
     * The Timeout stack. Its purpose is to know if tiles are corrupted when incrementing it dynamically.
     */
    protected Stack<Boolean> timeoutStack;
    /**
     * The Strategic values. Strategic board values.
     */
    protected int[][] strategicValues;
    /**
     * The Threads.
     */
    protected ArrayList<Thread> threads;
    private int complexity = 5;

    /**
     * Instantiates a new Abstract minimax.
     *
     * @param strategicValues the strategic values
     * @param depth           the depth
     * @param dynamicDepth    the dynamic depth
     */
    public Minimax(int[][] strategicValues, int depth, boolean dynamicDepth) {
        this.currentDepth = depth;
        this.dynamicDepth = dynamicDepth;
        this.strategicValues = strategicValues;
    }

    /**
     * Destroys all threads within the Minimax algorithm after the interrupt is called.
     */
    public void destroy() {
        if (timeoutThread != null) {
            timeoutThread.interrupt();
        }

        if (threads != null) {
            for (Thread thread : threads) {
                thread.interrupt();
            }
        }
    }

    /**
     * Sets the complexity of the game.
     *
     * @param complexity    The given complexity
     */
    public void setComplexity(int complexity) {
        this.complexity = complexity;

        switch (complexity) {
            case 1:
                setDepth(1);
                dynamicDepth = false;
                break;
            case 2:
                setDepth(2);
                dynamicDepth = false;
                break;
            case 3:
                setDepth(4);
                dynamicDepth = false;
                break;
            case 4:
                setDepth(6);
                dynamicDepth = false;
                break;
            case 5:
                setDepth(8);
                dynamicDepth = true;
                break;
        }
    }

    /**
     * Gets complexity.
     *
     * @return the complexity
     */
    public int getComplexity() {
        return complexity;
    }


    /**
     * Gets current seconds.
     *
     * @return the current seconds
     */
    public static long getCurrentSeconds() {
        return System.currentTimeMillis() / 1000;
    }

    /**
     * Seconds left long.
     *
     * @return the long
     */
    public long secondsLeft() {
        return (startTime + (timeout / 1000)) - getCurrentSeconds();
    }

    public void startTimeout(int timeout) {
        this.timeout = timeout;
        timedOut = false;
        startTime = getCurrentSeconds();

        timeoutThread = new Thread(() -> {
            try {
                Thread.sleep(timeout);
                triggerTimeout();
            } catch (InterruptedException ignored) {
            }
        });

        timeoutThread.start();
    }

    /**
     * Is timed out boolean.
     *
     * @return the boolean
     */
    public boolean isTimedOut() {
        return timedOut;
    }

    /**
     * Trigger timeout.
     */
    public synchronized void triggerTimeout() {
        timedOut = true;
        if (timeoutStack != null) {
            timeoutStack.push(true);
        }
    }
}
