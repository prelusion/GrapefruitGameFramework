package com.grapefruit.gamework.controller;

import com.grapefruit.gamework.games.Game;
import com.grapefruit.gamework.games.GameRegistry;
import com.grapefruit.gamework.view.templates.GameTile;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerMainWindow {

    @FXML
    private URL location;

    @FXML
    private VBox gamesVBox;

    @FXML
    private ResourceBundle resources;

    public ControllerMainWindow()
    {

    }

    @FXML
    private void initialize()
    {
        for (Game game: GameRegistry.games){
            gamesVBox.getChildren().add(new GameTile(game.getName(), game.getIcon()).getParent());
        }

    }


}
