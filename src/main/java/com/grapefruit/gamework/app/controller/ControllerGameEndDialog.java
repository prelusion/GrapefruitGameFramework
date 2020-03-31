package com.grapefruit.gamework.app.controller;

import com.grapefruit.gamework.app.model.IModel;
import com.grapefruit.gamework.app.model.ModelGameEndDialog;
import com.grapefruit.gamework.app.model.ModelGameTile;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerGameEndDialog implements IController{

    private ModelGameEndDialog model;


    @FXML
    private Text gameEndMessage;

    @FXML
    private URL location;

    @FXML
    private ResourceBundle resources;

    /**
     * Required for FXML
     */
    public ControllerGameEndDialog()
    {
    }

    /**
     * Required for FXML
     * Sets the displayed icon and name for listed game.
     */
    private void initialize()
    {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setModel(IModel model) {
        this.model = (ModelGameEndDialog) model;
        initialize();
    }
}
