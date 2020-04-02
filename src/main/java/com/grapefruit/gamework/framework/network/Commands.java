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
     * @param name String the username of the user to login
     */
    public static void login(ServerConnection conn, String name){
        conn.sendCommand(KEYWORD_LOGIN + name);
    }

    /**
     * The function logout logs the currently logged in user out.
     *
     * @param conn ServerConnection the object that handles the server connection.
     */
    public static void logout(ServerConnection conn){
        conn.sendCommand(KEYWORD_LOGOUT);
    }

    /**
     * This functions asks the server for a list of available games.
     *
     * @param conn ServerConnection the object that handles the server connection.
     */
    public static void getGameList(ServerConnection conn){
        conn.sendCommand(KEYWORD_GAMELIST);
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
        conn.sendCommand(KEYWORD_MOVE + Integer.toString((int)row * boardSize + col + 1));
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
