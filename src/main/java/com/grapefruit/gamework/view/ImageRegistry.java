package com.grapefruit.gamework.view;

import com.grapefruit.gamework.view.ResourceLoader;
import javafx.scene.image.Image;

public class ImageRegistry {

    private static ResourceLoader loader = new ResourceLoader();

    //icons
    public static Image GAME_ICON_OTHELLO = loader.loadImage("othello.png", ResourceLoader.ImageType.GAME_ICON);
    public static Image GAME_ICON_TICTACTOE = loader.loadImage("tictactoe.png", ResourceLoader.ImageType.GAME_ICON);
    public static Image GAMEWORK_ICON = loader.loadImage("icon.png", ResourceLoader.ImageType.GAME_ICON);

}
