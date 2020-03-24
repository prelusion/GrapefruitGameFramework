package com.grapefruit.gamework.view.presets;

import com.grapefruit.gamework.view.FXMLRegistry;
import com.grapefruit.gamework.view.IPresetWrapper;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class GameTile implements IPresetWrapper {

    private Parent gameTile = FXMLRegistry.buildGameTile();
    private ImageView gameIcon = (ImageView) gameTile.lookup("#game-image-icon");
    private Text gameName = (Text) gameTile.lookup("#game-text-name");
    private String name;
    private Image icon;

    public GameTile(String name, Image icon){
        this.name = name;
        this.icon = icon;



        update();
    }

    @Override
    public Parent getParent() {
        return gameTile;
    }

    @Override
    public void update() {
        gameIcon.setImage(icon);
        gameName.setText(name);
    }
}
