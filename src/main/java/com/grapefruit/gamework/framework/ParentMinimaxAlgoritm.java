package com.grapefruit.gamework.framework;
import java.util.ArrayList;
import java.util.Stack;

public abstract class ParentMinimaxAlgoritm implements IMinimaxAlgoritm {

    protected int currentDepth;
    protected boolean dynamicDepth = true;
    protected int timeout;
    protected long startTime;
    protected Thread timeoutThread;
    protected boolean timedOut;
    protected Stack<Boolean> timeoutStack;
    protected int[][] strategicValues;
    protected ArrayList<Thread> threads;

    public ParentMinimaxAlgoritm(int[][] strategicValues, int depth, boolean dynamicDepth) {
        this.currentDepth = depth;
        this.dynamicDepth = dynamicDepth;
        this.strategicValues = strategicValues;
    }

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

    public void setComplexity(int complexity) {
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

    public static long getCurrentSeconds() {
        return System.currentTimeMillis() / 1000;
    }

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

    public boolean isTimedOut() {
        return timedOut;
    }

    public synchronized void triggerTimeout() {
        System.out.println("Trigger Timeout!");
        timedOut = true;
        if(timeoutStack != null) {
            timeoutStack.push(true);
        }
    }
}
