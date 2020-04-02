package com.grapefruit.gamework.framework.network;

public class Command {

    private String commandString;
    private CommandCallback callback;

    public Command(String commandString, CommandCallback callback){
        this.commandString = commandString;
        this.callback = callback;
    }

    public void doCallBack(String[] args){
        callback.onResponse(args);
    }
}
