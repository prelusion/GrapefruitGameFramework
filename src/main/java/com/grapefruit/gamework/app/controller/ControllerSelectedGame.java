package com.grapefruit.gamework.app.controller;

import com.grapefruit.gamework.app.model.IModel;
import com.grapefruit.gamework.app.model.ModelSelectedGame;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.net.URL;

public class ControllerSelectedGame implements IController {

    private ModelSelectedGame model;

    @FXML
    private Button onlineButton;

    @FXML
    private Button offlineButton;

    @FXML
    private Text gameName;

    @FXML
    private ImageView gameIcon;

    @FXML
    private URL location;

    /**
     * Required for FXML
     */
    public ControllerSelectedGame()
    {
    }

    /**
     * Required for FXML
     * also updates list of games.
     */
    private void initialize()
    {
    }

    @Override
    public void setModel(IModel model) {
        this.model = (ModelSelectedGame) model;

        gameName.setText(this.model.getSelectedGame().getName());
        gameIcon.setImage(this.model.getSelectedGame().getIcon());
    }

}
