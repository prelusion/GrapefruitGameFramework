package com.grapefruit.gamework.app.controller;

import com.grapefruit.gamework.app.model.IModel;
import com.grapefruit.gamework.app.model.ModelGameTile;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerTableSetting implements IController{


    private ModelGameTile model;

    @FXML
    private URL location;

    @FXML
    private ResourceBundle resources;

    public ControllerTableSetting()
    {
    }

    private void initialize()
    {

    }

    @Override
    public void setModel(IModel model) {
        this.model = (ModelGameTile) model;
        initialize();
    }
}
