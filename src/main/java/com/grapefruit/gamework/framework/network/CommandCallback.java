package com.grapefruit.gamework.framework.network;

import java.util.EventListener;

public abstract class CommandCallback implements EventListener {

    public abstract void onResponse(boolean success, String[] args);

}
