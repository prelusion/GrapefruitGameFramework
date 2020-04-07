package com.grapefruit.gamework.app.controller;

import com.grapefruit.gamework.app.GameApplication;
import com.grapefruit.gamework.app.model.IModel;
import com.grapefruit.gamework.app.model.ModelLobbyBrowser;
import com.grapefruit.gamework.app.model.ModelSelectedGame;
import com.grapefruit.gamework.app.view.templates.LobbyBrowser.LobbyBrowserFactory;
import com.grapefruit.gamework.framework.Colors;
import com.grapefruit.gamework.framework.Player;
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
    public ControllerSelectedGame() {
    }

    /**
     * Required for FXML
     * also updates list of games.
     */
    private void initialize() {
    }

    @Override
    public void setModel(IModel model) {
        this.model = (ModelSelectedGame) model;
        gameName.setText(this.model.getSelectedGame().getAssets().getDisplayName());
        gameIcon.setImage(this.model.getSelectedGame().getAssets().getIcon());
        setMainMenuButtons();
    }

    public void setMainMenuButtons() {
        ArrayList<Button> buttons = new ArrayList<>();
        Button onlineButton = new Button("Play online");
        Button offlineButton = new Button("Play offline");

        if (!model.isOnline()) {
            onlineButton.setDisable(true);
        }

        ControllerSelectedGame controller = this;
        onlineButton.setOnAction(event -> {
            buttonBox.getChildren().removeAll(buttonBox.getChildren());
            buttonBox.getChildren().add(LobbyBrowserFactory.build(
                    new ModelLobbyBrowser(
                            model.getServerManager(),
                            model.getOnlineName(),
                            controller,
                            model.getSelectedGame().getAssets(),
                            model.getSelectedGame())
                    ).getParent()
            );
        });

        offlineButton.setOnAction(event -> setOfflineOptions());

        buttons.add(onlineButton);
        buttons.add(offlineButton);
        layoutButtons(buttons);
    }

    private void setOfflineOptions() {
        ArrayList<Button> buttons = new ArrayList<>();
        Button aiButton = new Button("Play vs AI");
        Button friendButton = new Button("Play vs friend");

        aiButton.setOnAction(event -> {
            Player playerBlack = new Player(model.getOnlineName(), Colors.BLACK, false);
            Player playerWhite = new Player("Player 2", Colors.WHITE, true);
            Player[] players = new Player[]{playerBlack, playerWhite};

            GameApplication.startGame(
                    model.getSelectedGame().getAssets(),
                    model.getSelectedGame().getFactory().create(players),
                    players
            );
        });

        friendButton.setOnAction(event -> {
            Player playerBlack = new Player("Player 1", Colors.BLACK, true);
            Player playerWhite = new Player("Player 2", Colors.WHITE, true);

            Player[] players = new Player[]{playerBlack, playerWhite};

            GameApplication.startGame(
                    model.getSelectedGame().getAssets(),
                    model.getSelectedGame().getFactory().create(players),
                    players
            );
        });

        buttons.add(aiButton);
        buttons.add(friendButton);
        layoutButtons(buttons);
    }

    private void layoutButtons(ArrayList<Button> buttons) {
        int buttonSpacing = 80;
        buttonBox.getChildren().removeAll(buttonBox.getChildren());
        for (Button button : buttons) {
            Pane pane = new Pane();
            pane.minHeight(buttonSpacing);
            pane.setPrefHeight(buttonSpacing);
            buttonBox.getChildren().add(pane);
            buttonBox.getChildren().add(button);
        }
    }
}
