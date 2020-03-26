package com.grapefruit.gamework.app.controller;

import com.grapefruit.gamework.app.games.Game;
import com.grapefruit.gamework.app.model.IModel;
import com.grapefruit.gamework.app.model.ModelGameTile;
import com.grapefruit.gamework.app.model.ModelSettingsWindow;
import com.grapefruit.gamework.app.view.templates.GameTile.GameTileFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerSettingsWindow implements IController {

    private ModelSettingsWindow modelSettingsWindow= null;

    @FXML
    private URL location;

    @FXML
    private ResourceBundle resources;

    public ControllerSettingsWindow()
    {

    }

    private void initialize()
    {


    }


    @Override
    public void setModel(IModel model) {
        modelSettingsWindow = (ModelSettingsWindow) model;
        initialize();
    }

}
