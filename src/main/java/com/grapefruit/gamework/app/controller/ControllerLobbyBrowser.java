package com.grapefruit.gamework.app.controller;

import com.grapefruit.gamework.app.GameApplication;
import com.grapefruit.gamework.app.model.IModel;
import com.grapefruit.gamework.app.model.ModelGameEndDialog;
import com.grapefruit.gamework.app.model.ModelLobbyBrowser;
import com.grapefruit.gamework.app.util.Command;
import com.grapefruit.gamework.app.view.templates.GameEndDialogWindow.GameEndDialogFactory;
import com.grapefruit.gamework.framework.Colors;
import com.grapefruit.gamework.framework.Game;
import com.grapefruit.gamework.framework.Player;
import com.grapefruit.gamework.network.Commands;
import com.grapefruit.gamework.network.ServerConnection;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import javafx.util.Duration;

import java.util.List;
import java.util.ResourceBundle;

/**
 * The type Controller lobby browser.
 */
public class ControllerLobbyBrowser implements IController {

    private ModelLobbyBrowser model;


    @FXML
    private Button backButton;

    @FXML
    private TableView challengeTable;

    @FXML
    private ResourceBundle resources;

    @FXML
    private RadioButton aiRadioButton;

    /**
     * Required for FXML
     */
    public ControllerLobbyBrowser() {
    }

    /**
     * Required for FXML
     * Sets the displayed icon and name for listed game.
     */
    private void initialize() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setModel(IModel model) {
        this.model = (ModelLobbyBrowser) model;
        setupWidgets();
    }

    /**
     * Sets widgets.
     */
    public void setupWidgets() {
        setupTable();

        if (!model.getSelectedGame().getAssets().getDisplayName().equals("Reversi")) {
            aiRadioButton.setVisible(false);
        }

        Timeline timeline = new Timeline();

        KeyFrame keyFrame = new KeyFrame(
                Duration.millis(500),
                check -> {
                    if (!model.getServerManager().isConnected()) {
                        timeline.stop();
                        return;
                    }
                    updateTable();
                    challengeTable.sceneProperty().addListener((obs, oldScene, newScene) -> {
                        if (newScene == null) {
                            timeline.stop();
                        }
                    });
                });

        timeline.getKeyFrames().add(keyFrame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        updateTable();


    }

    private void setupTable() {
        TableColumn challengers = new TableColumn("Player");
        TableColumn status = new TableColumn("Status");
        TableColumn buttons = new TableColumn("Action");

        //
        // Source: https://stackoverflow.com/questions/29489366/how-to-add-button-in-javafx-table-view
        //
        buttons.setCellValueFactory(new PropertyValueFactory<>("status"));

        Callback<TableColumn<ChallengeablePlayer, String>, TableCell<ChallengeablePlayer, String>> cellFactory = param -> new PlayerTableCell();

        //
        // END
        //

        challengers.setCellValueFactory(new PropertyValueFactory<ControllerTableSetting.Setting, String>("playerName"));
        status.setCellValueFactory(new PropertyValueFactory<ControllerTableSetting.Setting, String>("status"));
        buttons.setCellFactory(cellFactory);

        challengeTable.getColumns().addAll(challengers, status, buttons);
    }

    private void updateTable() {
        if (challengeTable.isVisible()) {
            model.fetchChallenges();
            model.fetchPlayers();

            final ObservableList<ChallengeablePlayer> challengeablePlayers = FXCollections.observableArrayList();

            List<ServerConnection.ResponseChallenge> challenges = model.getChallenges();
            if (model.getPlayerNames() != null) {
                for (String player : model.getPlayerNames()) {
                    if (!player.equals(model.getOnlineName())) {
                        for (ServerConnection.ResponseChallenge challenge : challenges) {
                            if (challenge.getChallenger().equals(player)) {
                                String challengeStatus = "";
                                if (challenge.getStatus() == ServerConnection.ChallengeStatus.CHALLENGE_RECEIVED) {
                                    challengeStatus = "Received";
                                } else if (challenge.getStatus() == ServerConnection.ChallengeStatus.CHALLENGE_SENT) {
                                    challengeStatus = "Sent";
                                }
                                challengeablePlayers.add(new ChallengeablePlayer(challenge.getChallenger(), challengeStatus));
                                continue;
                            }
                        }
                        boolean found = false;
                        for (ChallengeablePlayer cPlayer : challengeablePlayers) {
                            if (cPlayer.playerName.equals(player)) {
                                found = true;
                            }
                        }
                        for (ServerConnection.ResponseChallenge challenge : model.getChallenges()) {
                            if (challenge.getChallenger().equals(player)) {
                                found = true;
                            }
                        }
                        if (!found) {
                            challengeablePlayers.add(new ChallengeablePlayer(player, "Unchallenged"));
                        }
                    }
                }

                challengeTable.setItems(challengeablePlayers);
            }
        }
    }

    @FXML
    private void onBack() {
        model.getControllerSelectedGame().setMainMenuButtons();
    }

    /**
     * The type Challengeable player.
     */
    public static class ChallengeablePlayer {

        private final SimpleStringProperty playerName;
        private final SimpleStringProperty status;

        private ChallengeablePlayer(String key, String value) {
            this.playerName = new SimpleStringProperty(key);
            this.status = new SimpleStringProperty(value);
        }

        /**
         * Gets player name.
         *
         * @return the player name
         */
        public String getPlayerName() {
            return playerName.get();
        }

        /**
         * Gets status.
         *
         * @return the status
         */
        public String getStatus() {
            return status.get();
        }

        /**
         * Sets status.
         *
         * @param value the value
         */
        public void setStatus(String value) {
            this.status.set(value);
        }
    }

    /**
     * Send challenge.
     *
     * @param player the player
     */
    public void sendChallenge(ChallengeablePlayer player) {
        model.getServerManager().queueCommand(Commands.challenge((success, args) -> {
            if (!success) {
                if (args != null) {
                    Platform.runLater(() -> {
                        ModelGameEndDialog endDialogModel = new ModelGameEndDialog(args[0], () -> {
                        }, false);
                        GameEndDialogFactory.build(endDialogModel);
                    });
                }
                return;
            }
            ServerConnection.ResponseChallenge responseChallenge = new ServerConnection.ResponseChallenge(
                    player.getPlayerName(),
                    -1,
                    model.getGameAssets().getServerId(),
                    ServerConnection.ChallengeStatus.CHALLENGE_SENT
            );

            model.getServerManager().addChallenge(responseChallenge);

            player.setStatus("Sent");
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    updateTable();
                }
            });
        }, player.getPlayerName(), model.getGameAssets().getServerId()));
    }

    /**
     * Accept challenge.
     *
     * @param challengeNumber the challenge number
     */
    public void acceptChallenge(int challengeNumber) {
        model.getServerManager().queueCommand(
                Commands.challengeRespond((success, args) -> {
                }, true, challengeNumber));
    }

    /**
     * Sets game start event handler.
     *
     * @param command the command
     */
    public void setupGameStartEventHandler(Command command) {
        model.getServerManager().setTurnCallback((boolean success2, String[] args2) -> {
            model.getServerManager().setTurnTooFast(true);
        });

        model.getServerManager().setMoveCallback((boolean success2, String[] args2) -> {
            model.getServerManager().setMoveTooFast(true);
            model.getServerManager().setMoveTooFastArgs(args2);
        });

        model.getServerManager().setStartGameCallback((success, args) -> {
            boolean isPlayingAsAI = aiRadioButton.isSelected();

            String firstTurnName = args[0];
            String opponentName = args[1];

            String currentPlayerName = model.getOnlineName();

            Player[] players = new Player[2];

            if (firstTurnName.equals(currentPlayerName)) {
                players[0] = new Player(currentPlayerName, Colors.BLACK, true, isPlayingAsAI);
                players[1] = new Player(opponentName, Colors.WHITE, false);
            } else if (firstTurnName.equals(opponentName)) {
                players[0] = new Player(opponentName, Colors.BLACK, false);
                players[1] = new Player(currentPlayerName, Colors.WHITE, true, isPlayingAsAI);
            }

            Game game = model.getSelectedGame().getFactory().create(players);

            Platform.runLater(() -> {
                command.execute();

                GameApplication.startOnlineGame(
                        model.getSelectedGame().getAssets(),
                        game,
                        model.getServerManager()
                );
            });
        });
    }

    private class PlayerTableCell extends TableCell<ChallengeablePlayer, String> {
        /**
         * The Btn.
         */
        final Button btn = new Button("");

        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);

            if (empty) {
                setGraphic(null);
                setText(null);
                return;
            }

            setGraphic(btn);
            setText(null);

            ChallengeablePlayer player = getTableView().getItems().get(getIndex());

            switch (player.getStatus()) {
                case "Received":
                    btn.setDisable(false);
                    btn.setText("Accept");
                    btn.setOnAction(event -> {
                        List<ServerConnection.ResponseChallenge> challenges = model.getChallenges();

                        ServerConnection.ResponseChallenge responseChallenge = challenges
                                .stream()
                                .filter(e -> e.getChallenger().equals(player.getPlayerName()))
                                .findFirst()
                                .orElse(null);

                        if (responseChallenge == null) {
                            return;
                        }


                        setupGameStartEventHandler(() -> {
                            model.getChallenges().clear();
                            model.getServerManager().clearChallenges();
                            player.setStatus("Unchallenged");
                            btn.setText("Send");
                            btn.setDisable(false);
                        });
                        acceptChallenge(responseChallenge.getNumber());
                    });
                    break;
                case "Sent":
                    btn.setText("Waiting");
                    btn.setDisable(true);
                    btn.setOnAction(event -> { /*Nothing*/ });
                    break;
                case "Unchallenged":
                    btn.setText("Send");
                    btn.setDisable(false);
                    btn.setOnAction(event -> {
                        setupGameStartEventHandler(() -> {
                            model.getChallenges().clear();
                            model.getServerManager().clearChallenges();
                            player.setStatus("Unchallenged");
                            btn.setText(null);
                            btn.setDisable(false);
                        });
                        sendChallenge(player);
                    });
                    break;
            }
        }
    }
}
