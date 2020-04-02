package com.grapefruit.gamework.framework.network;

import java.util.LinkedList;
import java.util.Queue;

public class ServerManager {

    private ServerConnection connection;
    private Queue<Command> commandQueue;

    public ServerManager(String serverAddress){
        this.connection = new ServerConnection(serverAddress);
        this.commandQueue = new LinkedList<>();
    }

    public void queueCommand(Command command){
        commandQueue.add(command);
    }

    public void respond(String[] args){

    }

    public Command findFirstFittingCommand(){
        return null;
        //Todo implement
    }
}
