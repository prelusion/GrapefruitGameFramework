package com.grapefruit.gamework.games.reversi;

import com.grapefruit.gamework.app.resources.ImageRegistry;
import com.grapefruit.gamework.framework.Assets;
import com.grapefruit.gamework.framework.TeamColor;
import javafx.scene.image.Image;


public class ReversiAssets extends Assets {

    public static Image PIECE_BLACK = ImageRegistry.GAME_PIECE_BLACK;
    public static Image PIECE_WHITE = ImageRegistry.GAME_PIECE_WHITE;
    private static Image ICON = ImageRegistry.GAME_ICON_OTHELLO;
    private static String DISPLAYNAME = "Reversi";

    @Override
    public String getDisplayName() {
        return DISPLAYNAME;
    }

    @Override
    public Image getIcon() {
        return ICON;
    }

    public Image getPieceImageByColor(TeamColor color){
        switch (color) {
            case BLACK:
                return PIECE_BLACK;

            case WHITE:
                return PIECE_WHITE;

            default:
                return null;
        }
    }
}
