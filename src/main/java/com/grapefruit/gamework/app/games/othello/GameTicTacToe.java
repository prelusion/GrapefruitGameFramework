package com.grapefruit.gamework.app.games.othello;

import com.grapefruit.gamework.app.games.Game;
import com.grapefruit.gamework.app.resources.ImageRegistry;
import javafx.scene.image.Image;

public class GameTicTacToe implements Game {

    private String name = "TicTacToe";
    private Image icon = ImageRegistry.GAME_ICON_TICTACTOE;


    @Override
    public String getName() {
        return name;
    }

    @Override
    public Image getIcon() {
        return icon;
    }
}
