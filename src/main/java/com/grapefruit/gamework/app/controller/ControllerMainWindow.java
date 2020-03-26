package com.grapefruit.gamework.controller;

import com.grapefruit.gamework.games.Game;
import com.grapefruit.gamework.games.GameRegistry;
import com.grapefruit.gamework.model.IModel;
import com.grapefruit.gamework.model.ModelGameTile;
import com.grapefruit.gamework.model.ModelMainWindow;
import com.grapefruit.gamework.view.templates.GameTile.GameTileFactory;
import com.grapefruit.gamework.view.templates.GameTile.TemplateGameTile;
import com.grapefruit.gamework.view.templates.MainWindow.MainWindowFactory;
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
