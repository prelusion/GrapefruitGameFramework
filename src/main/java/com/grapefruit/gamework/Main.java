package com.grapefruit.gamework;


import com.grapefruit.gamework.app.GameApplication;
import com.grapefruit.gamework.app.resources.AppSettings;

public class Main {

    public static void main(String[] args) {
        AppSettings.Settings settings = AppSettings.getSettings();
        GameApplication application = new GameApplication();
        application.startApplication(args);
    }

}
