package com.grapefruit.gamework.framework.network;

import java.util.ArrayList;
import java.util.List;

/**
 * The commands class holds static functions that map a command from the client to a server readable format.
 */
public abstract class Commands {

    private static final String KEYWORD_LOGIN = "login ";
    private static final String KEYWORD_LOGOUT = "logout";
    private static final String KEYWORD_GAMELIST = "get gamelist";
    private static final String KEYWORD_PLAYERLIST = "get playerlist";
    private static final String KEYWORD_HELP = "help";
    private static final String KEYWORD_MOVE = "move ";
    private static final String KEYWORD_FORFEIT = "forfeit";
    private static final String KEYWORD_CHALLENGE = "challenge ";

    /**
     * The login function sends a login command from the client to the server.
     *
     * @param conn ServerConnection the object that handles the server connection.
     */
     * @param name String the username of the user to login
    public static Command login(String name, CommandCallback callback){
        return new Command(KEYWORD_LOGIN+ name, callback, ServerManager.ResponseType.CONFIRMONLY);
    }

    public static Command logout(CommandCallback callback){
        return new Command(KEYWORD_LOGOUT, callback, ServerManager.ResponseType.CONFIRMONLY);
    /**
     * The function logout logs the currently logged in user out.
     *
     * @param conn ServerConnection the object that handles the server connection.
     */
    }

    public static Command getGameList(CommandCallback callback){
        return new Command(KEYWORD_GAME_LIST, callback, ServerManager.ResponseType.LIST);
    /**
     * This functions asks the server for a list of available games.
     *
     * @param conn ServerConnection the object that handles the server connection.
     */
    }

    /**
     * This function asks the server for a list of available players.
     *
     * @param conn ServerConnection the object that handles the server connection.
     */
    public static void getPlayerList(ServerConnection conn) {
        conn.sendCommand(KEYWORD_PLAYERLIST);
    }

    /**
     * This function asks the server for a list of all possible commands.
     *
     * @param conn ServerConnection the object that handles the server connection.
     */
    public static void getHelp(ServerConnection conn) {
        conn.sendCommand(KEYWORD_HELP);
    }

    /**
     * This functions asks the server to set a move.
     *
     * @param conn      ServerConnection the object that handles the server connection.
     * @param row       int the row on the board.
     * @param col       int the col on the board.
     * @param boardSize int the board size.
     */
    public static void setMove(ServerConnection conn, int row, int col, int boardSize) {
        double index = row * boardSize + col + 1;
        conn.sendCommand(KEYWORD_MOVE + index);
    }

    /**
     * Forfeit.
     *
     * @param conn ServerConnection the object that handles the server connection.
     */
    public static void forfeit(ServerConnection conn) {
        conn.sendCommand(KEYWORD_FORFEIT);
    }

    /**
     * The challenge function is used to indicate if the current logged in player accepts the challenge by passing a boolean value.
     *
     * @param conn   ServerConnection the object that handles the server connection.
     * @param accept boolean Flag to indicate if the challenge is accepted or not.
     */
    public static void challenge(ServerConnection conn, boolean accept) {
        if (accept) {
            conn.sendCommand(KEYWORD_CHALLENGE + "accept");
        } else {
            conn.sendCommand(KEYWORD_CHALLENGE + "decline");
        }
    }
}
