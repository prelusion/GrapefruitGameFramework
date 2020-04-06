package com.grapefruit.gamework.framework.network;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * The type Server manager.
 */
public class ServerManager {

    private boolean sending;
    public BooleanProperty connected = new SimpleBooleanProperty();

    /**
     * The enum Response type.
     */
    public enum ResponseType{
        /**
         * List response type.
         */
        LIST,
        /**
         * Single response type.
         */
        SINGLE,
        /**
         * Confirmonly response type.
         */
        CONFIRMONLY
    }


    private ServerConnection connection;
    private Queue<Command> commandQueue;
    private List<ServerConnection.ResponseChallenge> challenges;

    /**
     * Instantiates a new Server manager.
     */
    public ServerManager(){
        this.connection = new ServerConnection(this);
        this.commandQueue = new LinkedList<>();
        this.challenges = new ArrayList<>();
    }

    /**
     * Checks if the client is connected to the server.
     *
     * @return boolean if connected.
     */
    public boolean isConnected(){
        return connection.isConnected();
    }

    /**
     * Tries to connect to the server with the ip-address given in the parameter.
     *
     * @param address String the address.
     * @return boolean if connected.
     */
    public boolean connect(String address){
        try {
            connection.connect(address);
            connected.setValue(true);
        } catch (IOException e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public void disconnect(){
        ServerManager manager = this;
        queueCommand(Commands.logout(new CommandCallback() {
            @Override
            public void onResponse(boolean success, String[] args) {
            }
        }));
        connection = new ServerConnection(manager);
        connected.setValue(false);
    }

    /**
     * Adds a Command to the queue.
     *
     * @param command Command the command
     */
    public void queueCommand(Command command){
        commandQueue.add(command);
        if (!sending) {
            connection.startSending();
            sending = true;
        }
    }


    /**
     * Finds first fitting command.
     *
     * @param responseType ResponseType the response type.
     * @param isConfirmed  boolean is confirmed.
     * @return Command the command.
     */
    public Command findFirstFittingCommand(ResponseType responseType, boolean isConfirmed){
        for (Command command: commandQueue){
            if (command.getResponseType() == responseType && command.isConfirmed() == isConfirmed){
                return command;
            }
        }
        return null;
    }

    /**
     * Gets first unconfirmed command.
     *
     * @return Command the command.
     */
    public Command getFirstUnconfirmed(){
        for (Command command: commandQueue){
            if (!command.isConfirmed()){
                return command;
            }
        }
        return null;
    }

    /**
     * Get first unsent command.
     *
     * @return Command the command.
     */
    public synchronized Command getFirstUnsent(){
        for (Command command: commandQueue){
            if (!command.isSent()){
                return command;
            }
        }
        return null;
    }

    /**
     * Removes the command given in the parameter from the queue.
     *
     * @param command Command the command to be removed.
     */
    public void removeCommandFromQueue(Command command){
        commandQueue.remove(command);
    }

    /**
     * Returnes if there are Commands in the queue.
     *
     * @return the boolean
     */
    public boolean commandsInQueue(){
        return commandQueue.size() > 0;
    }

    public void addChallenge(ServerConnection.ResponseChallenge challenge){
        challenges.add(challenge);
    }

    public void removeChallengeById(int id){
        for (ServerConnection.ResponseChallenge challenge: challenges){
            if (challenge.getNumber() == id){
                challenges.remove(challenge);
            }
        }
    }

    public List<ServerConnection.ResponseChallenge> getChallenges() {
        return challenges;
    }

    public void cancelChallenge(ServerConnection.ResponseChallenge challenge){
        challenges.remove(challenge);
    }

    /**
     * Tries to disconnect from the server.
     *
     */
    public void disconnect() {
        try {
            connection.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

