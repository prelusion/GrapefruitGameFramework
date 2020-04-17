package com.grapefruit.gamework.network;

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
    /**
     * The Connected.
     */
    public BooleanProperty connected = new SimpleBooleanProperty();
    private boolean turnTooFast = false;
    private boolean moveTooFast = false;
    private String[] moveTooFastArgs;

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
//            System.out.println(e.getMessage());
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
     * Find a command in the queue by the String keyword if it exists
     *
     * @param keyword     the keyword
     * @param isConfirmed the is confirmed
     * @return Command command
     */
    public Command findByKeyword(String keyword, boolean isConfirmed) {
        for (Command command : commandQueue) {
            String commandKeyword = command.getCommandString();

            if (commandKeyword.contains(keyword.toLowerCase()) && command.isConfirmed() == isConfirmed) {
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
    public Command getFirstUnsent() {
        Iterator<Command> i = commandQueue.iterator();

        while (i.hasNext()) {
            Command command = i.next();
            if (command == null) break;

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

    /**
     * Add challenge.
     *
     * @param challenge the challenge
     */
    public void addChallenge(ServerConnection.ResponseChallenge challenge) {
        if (challenge.getStatus() == ServerConnection.ChallengeStatus.CHALLENGE_SENT) {
            Iterator<ServerConnection.ResponseChallenge> iterator = challenges.iterator();
            while (iterator.hasNext()) {
                ServerConnection.ResponseChallenge challenge1 = iterator.next();
                if (challenge1.getStatus() == ServerConnection.ChallengeStatus.CHALLENGE_SENT) {
                    iterator.remove();
                }
            }
        }
        challenges.add(challenge);
    }

    /**
     * Remove a pending challenge by its id.
     *
     * @param id the id
     */
    public void removeChallengeById(int id) {
        for (ServerConnection.ResponseChallenge challenge : challenges) {
            if (challenge.getNumber() == id) {
                challenges.remove(challenge);
            }
        }
    }

    /**
     * Get the current pending challenges.
     *
     * @return challenges
     */
    public List<ServerConnection.ResponseChallenge> getChallenges() {
        return challenges;
    }

    /**
     * Cancel a pending challenge.
     *
     * @param challenge the challenge
     */
    public void cancelChallenge(ServerConnection.ResponseChallenge challenge) {
        challenges.remove(challenge);
    }

    /**
     * Disconnect from the server.
     */
    public void disconnect() {
        try {
            connection.closeConnection();
            connected.setValue(false);
        } catch (Exception ignored) {} finally {
            commandQueue.clear();
            challenges.clear();
        }
    }

    /**
     * Set a callback to be notified when the game starts.
     *
     * @param callback the callback
     */
    public void setStartGameCallback(CommandCallback callback) {
        connection.setStartGameCallback(callback);
    }

    /**
     * Remove start game callback.
     */
    public void removeStartGameCallback() {
        connection.setStartGameCallback(null);
    }

    /**
     * Set a callback to be notified when a move has been played.
     *
     * @param callback the callback
     */
    public void setMoveCallback(CommandCallback callback) {
        connection.setMoveCallback(callback);
    }

    /**
     * Remove the move callback.
     */
    public void removeMoveCallback() {
        connection.setMoveCallback(null);
    }

    /**
     * Set a callback to be notified when given the turn.
     *
     * @param callback the callback
     */
    public void setTurnCallback(CommandCallback callback) {
        connection.setTurnCallback(callback);
    }

    /**
     * Remove the turn callback.
     */
    public void removeTurnCallback() {
        connection.setTurnCallback(null);
    }

    /**
     * Set the callback to be notified when the other player timed out.
     *
     * @param callback the callback
     */
    public void setTurnTimeoutWinCallback(CommandCallback callback) {
        connection.setTurnTimeoutWinCallback(callback);
    }

    /**
     * Remove the other player turn timed out callback.
     */
    public void removeTurnTimeoutWinCallback() {
        connection.setTurnTimeoutWinCallback(null);
    }

    /**
     * Set the callback to be notified when we timed out.
     *
     * @param callback the callback
     */
    public void setTurnTimeoutLoseCallback(CommandCallback callback) {
        connection.setTurnTimeoutLoseCallback(callback);
    }

    /**
     * Remove the callback when we timed out.
     */
    public void removeTurnTimeoutLoseCallback() {
        connection.setTurnTimeoutLoseCallback(null);
    }

    /**
     * Set the callback to be notified when the other played had an illegal move.
     *
     * @param callback the callback
     */
    public void setIllegalmoveWinCallback(CommandCallback callback) {
        connection.setIllegalmoveWinCallback(callback);
    }

    /**
     * Remove the callback for when the other player had an illegal move.
     */
    public void removeIllegalmoveWinCallback() {
        connection.setIllegalmoveWinCallback(null);
    }

    /**
     * Set the callback to be notified when we played an illegal move.
     *
     * @param callback the callback
     */
    public void setIllegalmoveLoseCallback(CommandCallback callback) {
        connection.setIllegalmoveLoseCallback(callback);
    }

    /**
     * Remove the callback when we played an illegal move.
     */
    public void removeIllegalmoveLoseCallback() {
        connection.setIllegalmoveLoseCallback(null);
    }

    /**
     * Set the callback to be notified when the other player forfeited.
     *
     * @param callback the callback
     */
    public void setOnPlayerForfeitCallback(CommandCallback callback) {
        connection.setOnPlayerForfeitCallback(callback);
    }

    /**
     * Remove the callback for when a player forfeited.
     */
    public void removeOnPlayerForfeitCallback() {
        connection.setOnPlayerForfeitCallback(null);
    }

    /**
     * Set the callback to be notified when the other player disconnected.
     *
     * @param callback the callback
     */
    public void setOnPlayerDisconnectCallback(CommandCallback callback) {
        connection.setOnPlayerDisconnectCallback(callback);
    }


    /**
     * Remove on player disconnect callback callback.
     */
    public void removeOnPlayerDisconnectCallbackCallback() {
        connection.setOnPlayerDisconnectCallback(null);
    }


    /**
     * Sets on new challenget callback.
     *
     * @param callback the callback
     */
    public void setOnNewChallengetCallback(CommandCallback callback) {
        connection.setOnNewChallengetCallback(callback);
    }

    public void setConnectedName(String connectedName) {
        this.connectedName = connectedName;
    }

    /**
     * Gets connected name.
     *
     * @return the connected name
     */
    public String getConnectedName() {
        return connectedName;
    }

    /**
     * Clear challenges.
     */
    public void clearChallenges() {
        challenges = new ArrayList<>();
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
     * This method is called when the turn has been sent from the server before the game session was initialized.
     *
     * @param value the value
     */
    public void setTurnTooFast(boolean value) {
        turnTooFast = value;
    }

    /**
     * This method is used to see if a turn was given before the game session was initialized.
     *
     * @return turn too fast
     */
    public boolean getTurnTooFast() {
        return turnTooFast;
    }

    /**
     * This method is called when a move has been sent from the server before the game session was initialized.
     *
     * @param value the value
     */
    public void setMoveTooFast(boolean value) {
        moveTooFast = value;
    }

    /**
     * This method is used to see if a move was given before the game session was initialized.
     *
     * @return move too fast
     */
    public boolean getMoveTooFast() {
        return moveTooFast;
    }

    /**
     * This method is used to set the move arguments when the move has been sent before the game session was initialized.
     *
     * @param args the args
     */
    public void setMoveTooFastArgs(String[] args) {
        moveTooFastArgs = args;
    }

    /**
     * This method is used to return the arguments from the move which was sent before the game session was initialized.
     *
     * @return string [ ]
     */
    public String[] getMoveTooFastArgs() {
        return moveTooFastArgs;
    }
}
