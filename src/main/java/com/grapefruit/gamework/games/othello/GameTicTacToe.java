package com.grapefruit.gamework.games.othello;

import com.grapefruit.gamework.games.Game;
import com.grapefruit.gamework.view.ImageRegistry;
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
