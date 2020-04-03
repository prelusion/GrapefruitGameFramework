package com.grapefruit.gamework.framework.network;

import java.util.EventListener;

/**
 * The abstract type CommandCallback.
 */
public abstract class CommandCallback implements EventListener {

    /**
     * abstract method for implementing a callback function.
     *
     * @param success boolean the success flag.
     * @param args    String[] the arguments.
     */
    public abstract void onResponse(boolean success, String[] args);

}
