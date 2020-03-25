package com.grapefruit.gamework.games.othello;

import com.grapefruit.gamework.games.Game;
import com.grapefruit.gamework.view.ImageRegistry;
import javafx.scene.image.Image;

public class GameOthello implements Game {

    private String name = "Othello";
    private Image icon = ImageRegistry.GAME_ICON_OTHELLO;


    @Override
    public String getName() {
        return name;
    }

    @Override
    public Image getIcon() {
        return icon;
    }
}
