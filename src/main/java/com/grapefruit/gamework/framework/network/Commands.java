package com.grapefruit.gamework.framework.network;

import java.util.ArrayList;
import java.util.List;

public abstract class Commands {

    private static String KEYWORD_LOGIN = "login ";
    private static String KEYWORD_LOGOUT = "logout";
    private static String KEYWORD_GAME_LIST = "get gamelist";

    public static Command login(String name, CommandCallback callback){
        return new Command(KEYWORD_LOGIN+ name, callback, ServerManager.ResponseType.CONFIRMONLY);
    }

    public static Command logout(CommandCallback callback){
        return new Command(KEYWORD_LOGOUT, callback, ServerManager.ResponseType.CONFIRMONLY);
    }

    public static Command getGameList(CommandCallback callback){
        return new Command(KEYWORD_GAME_LIST, callback, ServerManager.ResponseType.LIST);
    }


}
