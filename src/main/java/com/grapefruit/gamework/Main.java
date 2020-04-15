package com.grapefruit.gamework;


import com.grapefruit.gamework.app.GameApplication;

/**
 * The type Main.
 */
public class Main {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        GameApplication application = new GameApplication();
        application.startApplication(args);
    }
}
