package com.grapefruit.gamework.framework.network;

import java.util.ArrayList;
import java.util.List;

public abstract class Commands {

    private static String KEYWORD_LOGIN = "login ";
    private static String KEYWORD_LOGOUT = "logout";
    private static String KEYWORD_GAMELIST = "get gamelist";

    public static void login(ServerConnection conn, String name){
        conn.sendCommand(KEYWORD_LOGIN + name);
    }

    public static void logout(ServerConnection conn){
        conn.sendCommand(KEYWORD_LOGOUT);
    }

    public static void getGameList(ServerConnection conn){
        conn.sendCommand(KEYWORD_GAMELIST);
    }
}
