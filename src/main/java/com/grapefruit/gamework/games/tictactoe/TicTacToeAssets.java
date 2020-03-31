package com.grapefruit.gamework.games.tictactoe;

import com.grapefruit.gamework.app.resources.ImageRegistry;
import com.grapefruit.gamework.framework.Assets;
import com.grapefruit.gamework.framework.TeamColor;
import javafx.scene.image.Image;

public class TicTacToeAssets extends Assets {

    public static Image PIECE_BLACK = ImageRegistry.TICTACTOE_GAME_PIECE_BLACK;
    public static Image PIECE_WHITE = ImageRegistry.TICTACTOE_GAME_PIECE_WHITE;
    private static Image ICON = ImageRegistry.GAME_ICON_TICTACTOE;
    private static String DISPLAYNAME = "TicTacToe";

    @Override
    public String getDisplayName() {
        return DISPLAYNAME;
    }

    @Override
    public Image getIcon() {
        return ICON;
    }

    @Override
    public Image getPieceImageByColor(TeamColor color) {
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

