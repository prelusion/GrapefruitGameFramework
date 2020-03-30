package com.grapefruit.gamework.app.controller;

import com.grapefruit.gamework.app.GameApplication;
import com.grapefruit.gamework.app.model.IModel;
import com.grapefruit.gamework.app.model.ModelSelectedGame;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;

public class ControllerSelectedGame implements IController {

    private ModelSelectedGame model;

    @FXML
    VBox buttonBox;

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
//        gameIcon.setImage(this.model.getSelectedGame().getIcon());
        setMainMenuButtons();
    }

    private void setMainMenuButtons(){
        ArrayList<Button> buttons = new ArrayList<>();
        Button onlineButton = new Button("Play online");
        Button offlineButton = new Button("Play offline");

        offlineButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setOfflineOptions();
            }
        });

        buttons.add(onlineButton);
        buttons.add(offlineButton);
        layoutButtons(buttons);
    }

    private void setOfflineOptions(){
        ArrayList<Button> buttons = new ArrayList<>();
        Button cpuButton = new Button("Play versus CPU");
        Button friendButton = new Button("Play versus friend");

        cpuButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });

        friendButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GameApplication.playGame(model.getSelectedGame());
            }
        });

        buttons.add(cpuButton);
        buttons.add(friendButton);
        layoutButtons(buttons);
    }

    private void layoutButtons(ArrayList<Button> buttons){
        int buttonSpacing = 80;
        buttonBox.getChildren().removeAll(buttonBox.getChildren());
        for (Button button: buttons) {
            Pane pane = new Pane();
            pane.minHeight(buttonSpacing);
            pane.setPrefHeight(buttonSpacing);
            buttonBox.getChildren().add(pane);
            buttonBox.getChildren().add(button);
        }
    }
}
