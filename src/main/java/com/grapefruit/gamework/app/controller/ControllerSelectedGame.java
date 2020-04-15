package com.grapefruit.gamework.app.controller;

import com.grapefruit.gamework.app.GameApplication;
import com.grapefruit.gamework.app.model.IModel;
import com.grapefruit.gamework.app.model.ModelLobbyBrowser;
import com.grapefruit.gamework.app.model.ModelSelectedGame;
import com.grapefruit.gamework.app.util.GlobalRandom;
import com.grapefruit.gamework.app.view.templates.LobbyBrowser.LobbyBrowserFactory;
import com.grapefruit.gamework.framework.Colors;
import com.grapefruit.gamework.framework.Game;
import com.grapefruit.gamework.framework.Player;
import com.grapefruit.gamework.network.Commands;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Control;
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
        ArrayList<Control> buttons = new ArrayList<>();

        Button tournamentButton = new Button("Tournament");
        Button autoChallengeButton = new Button("Auto challenge");
        Button onlineButton = new Button("Play online");
        Button offlineButton = new Button("Play offline");

        tournamentButton.setMinWidth(150);
        autoChallengeButton.setMinWidth(150);
        onlineButton.setMinWidth(150);
        offlineButton.setMinWidth(150);

        if (!model.isOnline()) {
            tournamentButton.setDisable(true);
            onlineButton.setDisable(true);
            autoChallengeButton.setDisable(true);
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

        if (model.getSelectedGame().getAssets().getDisplayName().equals("Reversi")) {
            buttons.add(tournamentButton);
            buttons.add(autoChallengeButton);
        }

        buttons.add(onlineButton);
        buttons.add(offlineButton);

        layoutButtons(buttons);
    }

    private void setOfflineOptions() {
        ArrayList<Control> buttons = new ArrayList<>();
        Button aiButton = new Button("Play vs AI");
        Button friendButton = new Button("Play vs friend");

        aiButton.setOnAction(event -> {

            Label label = new Label("Difficulty");
            label.setStyle("-fx-text-fill: white; -fx-font-size: 20px;");
            ChoiceBox<String> cb = new ChoiceBox<>(FXCollections.observableArrayList(
                    "Easy", "Medium", "Hard", "Extreme", "Impossible")
            );
            cb.getSelectionModel().selectFirst();
            Button startButton = new Button("Start");
            ArrayList<Control> control = new ArrayList<Control>();
            control.add(label);
            control.add(cb);
            control.add(startButton);
            layoutButtons(control);

            startButton.setOnAction(event2 -> {
                String difficultyString = cb.getValue();

                int difficulty = difficultyStringToInteger(difficultyString);

                Player playerBlack;
                Player playerWhite;

                if (GlobalRandom.getRandomGenerator().nextBoolean()) {
                    playerBlack = new Player("You", Colors.BLACK, true);
                    playerWhite = new Player("AI", Colors.WHITE, true, true);
                } else {
                    playerWhite = new Player("You", Colors.WHITE, true);
                    playerBlack = new Player("AI", Colors.BLACK, true, true);
                }

                Player[] players = new Player[]{playerBlack, playerWhite};

                Game game = model.getSelectedGame().getFactory().create(players, difficulty);

                GameApplication.startOfflineGame(
                        model.getSelectedGame().getAssets(),
                        game
                );
            });
        });

        friendButton.setOnAction(event -> {

            Player playerBlack;
            Player playerWhite;

            if (GlobalRandom.getRandomGenerator().nextBoolean()) {
                playerBlack = new Player("Player 1", Colors.BLACK, true);
                playerWhite = new Player("Player 2", Colors.WHITE, true);
            } else {
                playerWhite = new Player("Player 1", Colors.BLACK, true);
                playerBlack = new Player("Player 2", Colors.WHITE, true);
            }

            Player[] players = new Player[]{playerBlack, playerWhite};

            GameApplication.startOfflineGame(
                    model.getSelectedGame().getAssets(),
                    model.getSelectedGame().getFactory().create(players)
            );
        });

        if (model.getSelectedGame().getAssets().getDisplayName().equals("Reversi")) {
            buttons.add(aiButton);
        }

        buttons.add(friendButton);
        layoutButtons(buttons);
    }

    private void layoutButtons(ArrayList<Control> buttons) {
        int buttonSpacing = 80;
        buttonBox.getChildren().removeAll(buttonBox.getChildren());
        for (Control button : buttons) {
            Pane pane = new Pane();
            pane.minHeight(buttonSpacing);
            pane.setPrefHeight(buttonSpacing);
            buttonBox.getChildren().add(pane);
            buttonBox.getChildren().add(button);
        }
    }

    public void setupTournamentGameStartEventHandler() {
        model.getServerManager().setTurnCallback((boolean success2, String[] args2) -> {
            model.getServerManager().setTurnTooFast(true);
        });

        model.getServerManager().setMoveCallback((boolean success2, String[] args2) -> {
            model.getServerManager().setMoveTooFast(true);
            model.getServerManager().setMoveTooFastArgs(args2);
        });

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
                    Commands.challengeRespond((success2, args2) -> {
                    }, true, challengeNumber));
        });

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
                GameApplication.startAutoChallengeGame(
                        model.getSelectedGame().getAssets(),
                        model.getSelectedGame().getFactory().create(players),
                        model.getServerManager()
                );
            });
        });
    }

    int difficultyStringToInteger(String difficulty) {
        switch (difficulty) {
            case "Easy":
                return 1;
            case "Medium":
                return 2;
            case "Hard":
                return 3;
            case "Extreme":
                return 4;
            case "Impossible":
                return 5;
        }
        return -1;
    }
}
