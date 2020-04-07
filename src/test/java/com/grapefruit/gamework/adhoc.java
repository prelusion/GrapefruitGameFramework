package com.grapefruit.gamework;

public class adhoc {

    public static void main(String[] argv) {
        String str = "SVR GAME MATCH {PLAYERTOMOVE: \"bob\", GAMETYPE: \"Reversi\", OPPONENT: \"Guest\"}";
        String playerBlack = str.split("PLAYERTOMOVE: \"")[1].split("\"")[0];
        String playerWhite = str.split("OPPONENT: \"")[1].split("\"")[0];
        System.out.println(playerBlack);
        System.out.println(playerWhite);
    }
}
