package com.grapefruit.gamework.view;

import javafx.scene.Parent;

public abstract class FXMLRegistry {

    private static ResourceLoader loader = new ResourceLoader();


    public static Parent buildMainWindow(){
        return loader.loadFXML("mainwindow.fxml");
    }

    public static Parent buildGameTile(){
        return loader.loadFXML("game.fxml");
    }
}
