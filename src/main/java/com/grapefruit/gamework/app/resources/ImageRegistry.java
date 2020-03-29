package com.grapefruit.gamework.app.resources;

import javafx.scene.image.Image;

public class ImageRegistry {

    private static ResourceLoader loader = new ResourceLoader();

    //icons
    public static Image GAME_ICON_OTHELLO = loader.loadImage("othello.png", ResourceLoader.ImageType.GAME_ICON);
    public static Image GAME_ICON_TICTACTOE = loader.loadImage("tictactoe.png", ResourceLoader.ImageType.GAME_ICON);
    public static Image GAMEWORK_ICON = loader.loadImage("icon.png", ResourceLoader.ImageType.GAME_ICON);

    //Reversi
    public static Image GAME_PIECE_WHITE = loader.loadImage("piece_white.png", ResourceLoader.ImageType.ASSET);
    public static Image GAME_PIECE_BLACK = loader.loadImage("piece_black.png", ResourceLoader.ImageType.ASSET);
}
