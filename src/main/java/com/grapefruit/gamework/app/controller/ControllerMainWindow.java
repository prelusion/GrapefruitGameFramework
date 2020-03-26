package com.grapefruit.gamework.app.controller;

import com.grapefruit.gamework.app.games.Game;
import com.grapefruit.gamework.app.model.IModel;
import com.grapefruit.gamework.app.model.ModelGameTile;
import com.grapefruit.gamework.app.model.ModelMainWindow;
import com.grapefruit.gamework.app.view.templates.GameTile.GameTileFactory;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerMainWindow implements IController {

    private ModelMainWindow modelMainWindow= null;

    @FXML
    private URL location;

    @FXML
    private VBox gamesVBox;

    @FXML
    private ResourceBundle resources;

    public ControllerMainWindow()
    {

    }

    private void initialize()
    {
        for (Game game: modelMainWindow.getGames()){
            ModelGameTile tile = new ModelGameTile(game);
            gamesVBox.getChildren().add(GameTileFactory.build(tile).getParent());
        }

    }


    @Override
    public void setModel(IModel model) {
        modelMainWindow = (ModelMainWindow) model;
        initialize();
    }
}
