package com.grapefruit.gamework.app.resources;

import javafx.scene.image.Image;

/**
 * The type Image registry.
 */
public class ImageRegistry {

    private static ResourceLoader loader = new ResourceLoader();

    /**
     * The constant GAME_ICON_OTHELLO.
     */
//icons
    public static Image GAME_ICON_OTHELLO = loader.loadImage("othello.png", ResourceLoader.ImageType.GAME_ICON);
    /**
     * The constant GAME_ICON_TICTACTOE.
     */
    public static Image GAME_ICON_TICTACTOE = loader.loadImage("tictactoe.png", ResourceLoader.ImageType.GAME_ICON);
    /**
     * The constant GAMEWORK_ICON.
     */
    public static Image GAMEWORK_ICON = loader.loadImage("icon.png", ResourceLoader.ImageType.GAME_ICON);

    /**
     * The constant REVERSI_GAME_PIECE_WHITE.
     */
//Reversi
    public static Image REVERSI_GAME_PIECE_WHITE = loader.loadImage("reversi_piece_white.png", ResourceLoader.ImageType.ASSET);
    /**
     * The constant REVERSI_GAME_PIECE_BLACK.
     */
    public static Image REVERSI_GAME_PIECE_BLACK = loader.loadImage("reversi_piece_black.png", ResourceLoader.ImageType.ASSET);
    /**
     * The constant REVERSI_GAME_PIECE_GHOST.
     */
    public static Image REVERSI_GAME_PIECE_GHOST = loader.loadImage("reversi_piece_ghost.png", ResourceLoader.ImageType.ASSET);

    /**
     * The constant TICTACTOE_GAME_PIECE_WHITE.
     */
//TicTacToe
    public static Image TICTACTOE_GAME_PIECE_WHITE = loader.loadImage("tictactoe_piece_cross.png", ResourceLoader.ImageType.ASSET);
    /**
     * The constant TICTACTOE_GAME_PIECE_BLACK.
     */
    public static Image TICTACTOE_GAME_PIECE_BLACK = loader.loadImage("tictactoe_piece_circle.png", ResourceLoader.ImageType.ASSET);

    /**
     * The constant GREEN_BACKGROUND.
     */
//Game
    public static Image GREEN_BACKGROUND = loader.loadImage("green_board_background.png", ResourceLoader.ImageType.ASSET);
    /**
     * The constant WOOD_BACKGROUND.
     */
    public static Image WOOD_BACKGROUND = loader.loadImage("wood_background.png", ResourceLoader.ImageType.ASSET);
}
