package com.grapefruit.gamework.framework.network;

public class Command {

    private String commandString;
    private CommandCallback callback;
    private boolean confirmed = false;
    private ServerManager.ResponseType responseType;

    public Command(String commandString, CommandCallback callback, ServerManager.ResponseType responseType){
        this.commandString = commandString;
        this.callback = callback;
    }

    public void doCallBack(String[] args){
        callback.onResponse(args);
    }

    public void confirm(){
        confirmed = true;
    }

    public ServerManager.ResponseType getResponseType() {
        return responseType;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public String getCommandString() {return commandString;}
}
