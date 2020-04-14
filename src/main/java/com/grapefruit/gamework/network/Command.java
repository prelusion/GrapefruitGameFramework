package com.grapefruit.gamework.network;

/**
 * The type Command.
 */
public class Command {

    private String commandString;
    private CommandCallback callback;
    private boolean confirmed = false;
    private boolean sent = false;
    private ServerManager.ResponseType responseType;

    /**
     * Instantiates a new Command.
     *
     * @param commandString String the command string.
     * @param callback      CommandCallback the callback.
     * @param responseType  ResponseType the response type.
     */
    public Command(String commandString, CommandCallback callback, ServerManager.ResponseType responseType){
        this.commandString = commandString;
        this.callback = callback;
        this.responseType = responseType;
    }

    /**
     * Does callback on server response.
     *
     * @param success boolean the success.
     * @param args    String[] the args.
     */
    public void doCallBack(boolean success, String[] args){
        callback.onResponse(success, args);
    }

    /**
     * Confirm that the client received a response from the server for this command.
     *
     */
    public void confirm(){
        confirmed = true;
    }

    /**
     * Sets the sent state to true to indicate that this command has been sent to the server.
     *
     */
    public synchronized void send(){
        sent = true;
    }

    /**
     * Checks wether the command is sent or not.
     *
     * @return boolean is sent or not.
     */
    public synchronized boolean isSent(){
        return sent;
    }

    /**
     * Returns the responsetype of a command.
     *
     * @return ResponseType the type of response.
     */
    public ServerManager.ResponseType getResponseType() {
        return responseType;
    }

    /**
     * Checks if the client confirmed that it received a response of the server for this command.
     *
     * @return the boolean
     */
    public boolean isConfirmed() {
        return confirmed;
    }

    /**
     * Gets the command in string format.
     *
     * @return String the command string
     */
    public String getCommandString() {return commandString;}
}
