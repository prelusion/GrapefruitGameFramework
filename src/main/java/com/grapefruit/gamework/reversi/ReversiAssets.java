package com.grapefruit.gamework.reversi;

import com.grapefruit.gamework.app.resources.ImageRegistry;
import com.grapefruit.gamework.framework.Assets;
import com.grapefruit.gamework.framework.Team;
import javafx.scene.image.Image;


public class ReversiAssets extends Assets {

    public static Image PIECE_BLACK = ImageRegistry.GAME_PIECE_BLACK;
    public static Image PIECE_WHITE = ImageRegistry.GAME_PIECE_WHITE;

    public Image getPieceImageByColor(Team.TeamColour color){

        switch (color){
            case BLACK:
                return ReversiAssets.PIECE_BLACK;

            case WHITE:
                return ReversiAssets.PIECE_WHITE;

            default:
                return null;
        }
    }
}
