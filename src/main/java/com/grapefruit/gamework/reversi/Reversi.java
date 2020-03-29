package com.grapefruit.gamework.reversi;

import com.grapefruit.gamework.app.resources.ImageRegistry;
import com.grapefruit.gamework.framework.Game;
import com.grapefruit.gamework.framework.MoveSetter;
import javafx.scene.image.Image;

public class Reversi extends Game {

    public static Image IMAGE_BLACK_PIECE = ImageRegistry.GAME_PIECE_BLACK;
    public static Image IMAGE_WHITE_PIECE = ImageRegistry.GAME_PIECE_WHITE;

    public Reversi(MoveSetter moveSetter) {
        super(moveSetter, new ReversiBoard(8), "Reversi", ImageRegistry.GAME_ICON_OTHELLO);
    }


}
