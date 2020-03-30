package com.grapefruit.gamework.app.games.othello;

import com.grapefruit.gamework.app.resources.ImageRegistry;
import javafx.scene.image.Image;

public class GameTicTacToe {

    private String name = "GameTicTacToe";
    private Image icon = ImageRegistry.GAME_ICON_TICTACTOE;


    public String getName() {
        return name;
    }

    public Image getIcon() {
        return icon;
    }
}
