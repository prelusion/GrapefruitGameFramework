package com.grapefruit.gamework.framework.network;

import javax.security.auth.callback.Callback;
import java.util.ArrayList;
import java.util.List;

/**
 * The commands class holds static functions that map a command from the client to a server readable format.
 */
public abstract class Commands {

    private static final String KEYWORD_LOGIN = "login ";
    private static final String KEYWORD_LOGOUT = "logout";
    private static final String KEYWORD_GAME_LIST = "get gamelist";
    private static final String KEYWORD_PLAYER_LIST = "get playerlist";
    private static final String KEYWORD_HELP = "help ";
    private static final String KEYWORD_MOVE = "move ";
    private static final String KEYWORD_FORFEIT = "forfeit";
    private static final String KEYWORD_CHALLENGE = "challenge ";

    /**
     * The login function sends a login command from the client to the server.
     *
     * @param callback CommandCallback callback to execute after response has been received.
     * @param name String the username of the user to login
     */
    public static Command login(String name, CommandCallback callback){
        return new Command(KEYWORD_LOGIN+ name, callback, ServerManager.ResponseType.CONFIRMONLY);
    }

    /**
     * The function logout logs the currently logged in user out.
     *
     * @param callback CommandCallback callback to execute after response has been received.
     */
    public static Command logout(CommandCallback callback){
        return new Command(KEYWORD_LOGOUT, callback, ServerManager.ResponseType.CONFIRMONLY);
    }

    /**
     * This functions asks the server for a list of available games.
     *
     * @param callback CommandCallback callback to execute after response has been received.
     */
    public static Command getGameList(CommandCallback callback){
        return new Command(KEYWORD_GAME_LIST, callback, ServerManager.ResponseType.LIST);
    }

    /**
     * This function asks the server for a list of available players.
     *
     * @param callback CommandCallback callback to execute after response has been received.
     */
    public static Command getPlayerList(CommandCallback callback) {
        return new Command(KEYWORD_PLAYER_LIST, callback, ServerManager.ResponseType.LIST);
    }

    /**
     * This function asks the server for a list of all possible commands.
     *
     * @param callback CommandCallback callback to execute after response has been received.
     */
    public static Command getHelp(CommandCallback callback, String command) {
        return new Command(KEYWORD_HELP + command, callback, ServerManager.ResponseType.LIST);
    }

    /**
     * This functions asks the server to set a move.
     *
     * @param callback  CommandCallback callback to execute after response has been received.
     * @param row       int the row on the board.
     * @param col       int the col on the board.
     * @param boardSize int the board size.
     */
    public static Command setMove(CommandCallback callback, int row, int col, int boardSize) {
        int index = row  * boardSize + col;
        System.out.println("SEND INDEX: " + index);
        return new Command(KEYWORD_MOVE + index, callback, ServerManager.ResponseType.CONFIRMONLY);
    }

    /**
     * Forfeit.
     *
     * @param callback CommandCallback the object that handles the server connection.
     */
    public static Command forfeit(CommandCallback callback) {
        return new Command(KEYWORD_FORFEIT, callback, ServerManager.ResponseType.CONFIRMONLY);
    }

    /**
     * The challenge function is used to indicate if the current logged in player accepts the challenge by passing a boolean value.
     *
     * @param callback CommandCallback callback to execute after response has been received.
     * @param accept boolean Flag to indicate if the challenge is accepted or not.
     */
    public static Command challengeRespond(CommandCallback callback, boolean accept) {
        if (accept) {
            return new Command(KEYWORD_CHALLENGE + "accept", callback, ServerManager.ResponseType.CONFIRMONLY);
        } else {
            return new Command(KEYWORD_CHALLENGE + "decline", callback, ServerManager.ResponseType.CONFIRMONLY);
        }
    }

    public static Command challenge(CommandCallback callback, String playerName, String gameName) {
        return new Command(KEYWORD_CHALLENGE + "\"" + playerName + "\" \"" + gameName + "\"", callback, ServerManager.ResponseType.CONFIRMONLY);
    }
}
