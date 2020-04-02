package com.grapefruit.gamework.framework.network;

import java.util.LinkedList;
import java.util.Queue;

public class ServerManager {

    public enum ResponseType{
        LIST,
        SINGLE,
        CONFIRMONLY
    }


    private ServerConnection connection;
    private Queue<Command> commandQueue;

    public ServerManager(String serverAddress){
        this.connection = new ServerConnection(serverAddress, this);
        this.commandQueue = new LinkedList<>();
    }

    public void queueCommand(Command command){
        commandQueue.add(command);
    }

    public void respond(String[] args){

    }

    public Command findFirstFittingCommand(ResponseType responseType, boolean isConfirmed){
        for (Command command: commandQueue){
            if (command.getResponseType() == responseType && command.isConfirmed() == isConfirmed){
                return command;
            }
        }
        return null;
    }

    public Command getFirstUnconfirmed(){
        for (Command command: commandQueue){
            if (command.isConfirmed() == false){
                return command;
            }
        }
        return null;
    }

    public void removeCommandFromQueue(Command command){
        commandQueue.remove(command);
    }
}
