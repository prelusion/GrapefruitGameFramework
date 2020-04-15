package com.grapefruit.gamework.network;

import java.util.EventListener;

/**
 * The abstract type CommandCallback.
 */
public interface CommandCallback extends EventListener {

    /**
     * method for implementing a callback function.
     *
     * @param success boolean the success flag.
     * @param args    String[] the arguments.
     */
    void onResponse(boolean success, String[] args);
}
