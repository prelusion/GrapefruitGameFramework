package com.grapefruit.gamework.view.presets;

import com.grapefruit.gamework.view.FXMLRegistry;
import com.grapefruit.gamework.view.IPresetWrapper;
import javafx.scene.Parent;
import javafx.scene.image.Image;

public class GameTile implements IPresetWrapper {

    private Parent gameTile = FXMLRegistry.buildGameTile();
    private String name;
    private Image icon;

    public GameTile(String name, Image icon){
        this.name = name;
        this.icon = icon;
    }

    @Override
    public Parent getParent() {
        return gameTile;
    }

    @Override
    public void init() {

    }
}
