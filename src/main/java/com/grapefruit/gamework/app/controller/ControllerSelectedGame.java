package com.grapefruit.gamework.app.controller;

import com.grapefruit.gamework.app.GameApplication;
import com.grapefruit.gamework.app.model.IModel;
import com.grapefruit.gamework.app.model.ModelLobbyBrowser;
import com.grapefruit.gamework.app.model.ModelSelectedGame;
import com.grapefruit.gamework.app.view.templates.LobbyBrowser.LobbyBrowserFactory;
import com.grapefruit.gamework.framework.Colors;
import com.grapefruit.gamework.framework.Player;
import com.grapefruit.gamework.framework.network.Commands;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
        setupWidgets();
    }

    public void setupWidgets() {
        gameName.setText(this.model.getSelectedGame().getAssets().getDisplayName());
        gameIcon.setImage(this.model.getSelectedGame().getAssets().getIcon());
        setMainMenuButtons();
    }

    public void setMainMenuButtons() {
        ArrayList<Button> buttons = new ArrayList<>();

        Button tournamentButton = new Button("Tournament");
        Button autoChallengeButton = new Button("Auto challenge");
        Button onlineButton = new Button("Play online");
        Button offlineButton = new Button("Play offline");

        if (!model.isOnline()) {
            tournamentButton.setDisable(true);
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

        tournamentButton.setOnAction(event -> {
            buttonBox.getChildren().removeAll(buttonBox.getChildren());
            Label label = new Label();
            label.setText("tournament on");
            label.setStyle("-fx-text-fill: white; -fx-font-size: 20px;");
            buttonBox.getChildren().add(label);
            setupTournamentGameStartEventHandler();
        });

        autoChallengeButton.setOnAction(event -> {
            buttonBox.getChildren().removeAll(buttonBox.getChildren());
            Label label = new Label();
            label.setText("waiting...");
            label.setStyle("-fx-text-fill: white; -fx-font-size: 20px;");
            buttonBox.getChildren().add(label);
            setupAutoChallengeGameStartEventHandler();
        });

        offlineButton.setOnAction(event -> setOfflineOptions());

        buttons.add(tournamentButton);
        buttons.add(autoChallengeButton);
        buttons.add(onlineButton);
        buttons.add(offlineButton);
        layoutButtons(buttons);
    }

    private void setOfflineOptions() {
        ArrayList<Button> buttons = new ArrayList<>();
        Button aiButton = new Button("Play vs AI");
        Button friendButton = new Button("Play vs friend");

        aiButton.setOnAction(event -> {
            Player playerBlack = new Player("You", Colors.BLACK, true);
            Player playerWhite = new Player("AI", Colors.WHITE, true, true);

            Player[] players = new Player[]{playerBlack, playerWhite};

            GameApplication.startOfflineGame(
                    model.getSelectedGame().getAssets(),
                    model.getSelectedGame().getFactory().create(players)
            );
        });

        friendButton.setOnAction(event -> {
            Player playerBlack = new Player("Player 1", Colors.BLACK, true);
            Player playerWhite = new Player("Player 2", Colors.WHITE, true);

            Player[] players = new Player[]{playerBlack, playerWhite};

            GameApplication.startOfflineGame(
                    model.getSelectedGame().getAssets(),
                    model.getSelectedGame().getFactory().create(players)
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

    public void setupTournamentGameStartEventHandler() {
        model.getServerManager().removeStartGameCallback();
        model.getServerManager().setStartGameCallback((success, args) -> {
            String firstTurnName = args[0];
            String opponentName = args[1];

            String currentPlayerName = model.getOnlineName();

            Player[] players = new Player[2];

            if (firstTurnName.equals(currentPlayerName)) {
                players[0] = new Player(currentPlayerName, Colors.BLACK, true, true);
                players[1] = new Player(opponentName, Colors.WHITE, false);
            } else if (firstTurnName.equals(opponentName)) {
                players[0] = new Player(opponentName, Colors.BLACK, false);
                players[1] = new Player(currentPlayerName, Colors.WHITE, true, true);
            }

            Platform.runLater(() -> {
                GameApplication.startTournamentGame(
                        model.getSelectedGame().getAssets(),
                        model.getSelectedGame().getFactory().create(players),
                        model.getServerManager()
                );
            });
        });
    }

    public void setupAutoChallengeGameStartEventHandler() {
        model.getServerManager().setOnNewChallengetCallback((success, args) -> {
            int challengeNumber = Integer.parseInt(args[0]);

            model.getServerManager().queueCommand(
                    Commands.challengeRespond((success2, args2) -> {}, true, challengeNumber));
        });

        model.getServerManager().setStartGameCallback((success, args) -> {
            System.out.println("setupAutoChallengeGameStartEventHandler start game callback!!");
            String firstTurnName = args[0];
            String opponentName = args[1];

            String currentPlayerName = model.getOnlineName();

            Player[] players = new Player[2];

            if (firstTurnName.equals(currentPlayerName)) {
                players[0] = new Player(currentPlayerName, Colors.BLACK, true, true);
                players[1] = new Player(opponentName, Colors.WHITE, false);
            } else if (firstTurnName.equals(opponentName)) {
                players[0] = new Player(opponentName, Colors.BLACK, false);
                players[1] = new Player(currentPlayerName, Colors.WHITE, true, true);
            }

            Platform.runLater(() -> {
                GameApplication.startAutoChallengeGame(
                        model.getSelectedGame().getAssets(),
                        model.getSelectedGame().getFactory().create(players),
                        model.getServerManager()
                );
            });
        });
    }
}
