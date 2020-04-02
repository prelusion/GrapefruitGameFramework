package com.grapefruit.gamework.framework.network;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class ServerManager {

    private boolean sending;

    public enum ResponseType{
        LIST,
        SINGLE,
        CONFIRMONLY
    }


    private ServerConnection connection;
    private Queue<Command> commandQueue;

    public ServerManager(){
        this.connection = new ServerConnection(this);
        this.commandQueue = new LinkedList<>();
    }

    public boolean isConnected(){
        return connection.isConnected();
    }

    public boolean connect(String address){
        try {
            connection.connect(address);
        } catch (IOException e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public void queueCommand(Command command){
        commandQueue.add(command);
        if (!sending) {
            connection.startSending();
            sending = true;
        }
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
            if (!command.isConfirmed()){
                return command;
            }
        }
        return null;
    }

    public Command getFirstUnsent(){
        for (Command command: commandQueue){
            if (!command.isSent()){
                return command;
            }
        }
        return null;
    }

    public void removeCommandFromQueue(Command command){
        commandQueue.remove(command);
    }

    public boolean commandsInQueue(){
        return commandQueue.size() > 0;
    }
}

