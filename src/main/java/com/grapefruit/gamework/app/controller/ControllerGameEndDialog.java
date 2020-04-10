package com.grapefruit.gamework.app.controller;

import com.grapefruit.gamework.app.GameApplication;
import com.grapefruit.gamework.app.model.IModel;
import com.grapefruit.gamework.app.model.ModelGameEndDialog;
import com.grapefruit.gamework.app.model.ModelGameTile;
import com.grapefruit.gamework.app.util.Command;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerGameEndDialog implements IController {

    private ModelGameEndDialog model;


    @FXML
    private Button backButton;

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
        gameEndMessage.setText(this.model.getGameResult());
    }

    @FXML
    private void onBack(){
        Command command = model.getOnCloseGameCommand();
        command.execute();

        GameApplication.openLauncher();
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
    }
}
