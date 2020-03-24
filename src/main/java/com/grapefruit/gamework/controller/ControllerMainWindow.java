package com.grapefruit.gamework.controller;

import com.grapefruit.gamework.games.Game;
import com.grapefruit.gamework.games.GameRegistry;
import com.grapefruit.gamework.view.presets.GameTile;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerMainWindow {

    @FXML
    private URL location;

    @FXML
    private FlowPane gamesFlowPane;

    @FXML
    private ResourceBundle resources;

    public ControllerMainWindow()
    {

    }

    @FXML
    private void initialize()
    {
        for (Game game: GameRegistry.games){
            gamesFlowPane.getChildren().add(new GameTile(game.getName(), game.getIcon()).getParent());
        }
    }


}
