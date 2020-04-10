package com.grapefruit.gamework.framework.network;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.io.IOException;
import java.util.*;

/**
 * The type Server manager.
 */
public class ServerManager {

    private boolean sending;
    private String connectedName = null;
    public BooleanProperty connected = new SimpleBooleanProperty();

    /**
     * The enum Response type.
     */
    public enum ResponseType {
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
    public ServerManager() {
        this.connection = new ServerConnection(this);
        this.commandQueue = new LinkedList<>();
        this.challenges = new ArrayList<>();
    }

    /**
     * Checks if the client is connected to the server.
     *
     * @return boolean if connected.
     */
    public boolean isConnected() {
        return connection.isConnected();
    }

    /**
     * Tries to connect to the server with the ip-address given in the parameter.
     *
     * @param address String the address.
     * @return boolean if connected.
     */
    public boolean connect(String address) {
        try {
            connection.connect(address);
            connected.setValue(true);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Adds a Command to the queue.
     *
     * @param command Command the command
     */
    public void queueCommand(Command command) {
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
    public Command findFirstFittingCommand(ResponseType responseType, boolean isConfirmed) {
        for (Command command : commandQueue) {
            if (command.getResponseType() == responseType && command.isConfirmed() == isConfirmed) {
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
    public Command getFirstUnconfirmed() {
        for (Command command : commandQueue) {
            if (!command.isConfirmed()) {
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
    public synchronized Command getFirstUnsent() {
        Iterator<Command> i = commandQueue.iterator();
        while (i.hasNext()) {
            Command command = i.next();
            if (!command.isSent()) {
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
    public void removeCommandFromQueue(Command command) {
        commandQueue.remove(command);
    }

    /**
     * Returnes if there are Commands in the queue.
     *
     * @return the boolean
     */
    public boolean commandsInQueue() {
        return commandQueue.size() > 0;
    }

    public void addChallenge(ServerConnection.ResponseChallenge challenge) {
        if (challenge.getStatus() == ServerConnection.ChallengeStatus.CHALLENGE_SENT){
            Iterator<ServerConnection.ResponseChallenge> iterator = challenges.iterator();
            while (iterator.hasNext()){
                ServerConnection.ResponseChallenge challenge1 = iterator.next();
                if (challenge1.getStatus() == ServerConnection.ChallengeStatus.CHALLENGE_SENT){
                    iterator.remove();
                }
            }
        }
        challenges.add(challenge);
    }

    public void removeChallengeById(int id) {
        for (ServerConnection.ResponseChallenge challenge : challenges) {
            if (challenge.getNumber() == id) {
                challenges.remove(challenge);
            }
        }
    }

    public List<ServerConnection.ResponseChallenge> getChallenges() {
        return challenges;
    }

    public void cancelChallenge(ServerConnection.ResponseChallenge challenge) {
        challenges.remove(challenge);
    }

    /**
     * Tries to disconnect from the server.
     */
    public void disconnect() {
        try {
            connection.closeConnection();
            connected.setValue(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setStartGameCallback(CommandCallback callback) {
        connection.setStartGameCallback(callback);
    }

    public void removeStartGameCallback() {
        connection.setStartGameCallback(null);
    }

    public void setMoveCallback(CommandCallback callback) {
        connection.setMoveCallback(callback);
    }

    public void removeMoveCallback() {
        connection.setMoveCallback(null);
    }

    public void setTurnCallback(CommandCallback callback) {
        connection.setTurnCallback(callback);
    }

    public void removeTurnCallback() {
        connection.setTurnCallback(null);
    }

    public void setTurnTimeoutWinCallback(CommandCallback callback) {
        connection.setTurnTimeoutWinCallback(callback);
    }

    public void removeTurnTimeoutWinCallback() {
        connection.setTurnTimeoutWinCallback(null);
    }

    public void setTurnTimeoutLoseCallback(CommandCallback callback) {
        connection.setTurnTimeoutLoseCallback(callback);
    }

    public void removeTurnTimeoutLoseCallback() {
        connection.setTurnTimeoutLoseCallback(null);
    }

    public void setIllegalmoveWinCallback(CommandCallback callback) {
        connection.setIllegalmoveWinCallback(callback);
    }

    public void removeIllegalmoveWinCallback() {
        connection.setIllegalmoveWinCallback(null);
    }

    public void setOnPlayerForfeitCallback(CommandCallback callback) {
        connection.setOnPlayerForfeitCallback(callback);
    }

    public void removeOnPlayerForfeitCallback() {
        connection.setOnPlayerForfeitCallback(null);
    }

    public void setOnPlayerDisconnectCallback(CommandCallback callback) {
        connection.setOnPlayerDisconnectCallback(callback);
    }

    public void removeOnPlayerDisconnectCallbackCallback() {
        connection.setOnPlayerDisconnectCallback(null);
    }

    public void setOnNewChallengetCallback(CommandCallback callback) {
        connection.setOnNewChallengetCallback(callback);
    }

    public void setConnectedName(String connectedName) {
        this.connectedName = connectedName;
    }

    public String getConnectedName() {
        return connectedName;
    }

    public void clearChallenges() {
        challenges = new ArrayList<>();
    }
}
