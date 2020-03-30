package com.grapefruit.gamework.games.tictactoe;

import com.grapefruit.gamework.app.resources.ImageRegistry;
import com.grapefruit.gamework.framework.Assets;
import com.grapefruit.gamework.framework.Colors;
import javafx.scene.image.Image;

public class TicTacToeAssets extends Assets {


    private static Image ICON = ImageRegistry.GAME_ICON_TICTACTOE;
    private static String DISPLAYNAME = "Tic Tac Toe";

    @Override
    public String getDisplayName() {
        return DISPLAYNAME;
    }

    @Override
    public Image getIcon() {
        return ICON;
    }

    @Override
    public Image getPieceImageByColor(Colors color) {
        return null;
    }

}
