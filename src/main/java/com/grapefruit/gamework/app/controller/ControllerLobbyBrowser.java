package com.grapefruit.gamework.app.controller;

import com.grapefruit.gamework.app.model.IModel;
import com.grapefruit.gamework.app.model.ModelLobbyBrowser;
import com.grapefruit.gamework.framework.network.CommandCallback;
import com.grapefruit.gamework.framework.network.Commands;
import com.grapefruit.gamework.framework.network.ServerConnection;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import javafx.util.Duration;

import java.util.List;
import java.util.ResourceBundle;

public class ControllerLobbyBrowser implements IController{

    private ModelLobbyBrowser model;


    @FXML
    private Button backButton;

    @FXML
    private TableView challengeTable;

    @FXML
    private ResourceBundle resources;

    /**
     * Required for FXML
     */
    public ControllerLobbyBrowser()
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
        this.model = (ModelLobbyBrowser) model;
        initialize();
        setupTable();
        Timeline timeline= new Timeline(new KeyFrame(
                Duration.millis(500),
                check ->
                        updateTable()

        ));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        updateTable();
    }

    private void setupTable(){
        TableColumn challengers = new TableColumn("Player");
        TableColumn status = new TableColumn("Status");
        TableColumn buttons = new TableColumn("Action");

        //
        // Source: https://stackoverflow.com/questions/29489366/how-to-add-button-in-javafx-table-view
        //
        buttons.setCellValueFactory(new PropertyValueFactory<>("status"));

        Callback<TableColumn<ChallengeablePlayer, String>, TableCell<ChallengeablePlayer, String>> cellFactory
                = //
                new Callback<TableColumn<ChallengeablePlayer, String>, TableCell<ChallengeablePlayer, String>>() {
                    @Override
                    public TableCell call(final TableColumn<ChallengeablePlayer, String> param) {
                        final TableCell<ChallengeablePlayer, String> cell = new TableCell<ChallengeablePlayer, String>() {

                            final Button btn = new Button("");

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    ChallengeablePlayer player = getTableView().getItems().get(getIndex());
                                    if (player.getStatus().equals("Received")) {
                                        btn.setDisable(false);
                                        btn.setText("Accept");
                                        btn.setOnAction(event -> {
                                            //todo start game

                                        });
                                    } else if (player.getStatus().equals("Sent")) {
                                        btn.setText("Waiting");
                                        btn.setDisable(true);
                                        btn.setOnAction(event -> {
                                            //Nothing
                                        });
                                    } else if (player.getStatus().equals("Unchallenged")) {
                                        btn.setText("Send");
                                        btn.setDisable(false);
                                        btn.setOnAction(event -> {
                                            model.getServerManager().queueCommand(Commands.challenge(new CommandCallback() {
                                                @Override
                                                public void onResponse(boolean success, String[] args) {
                                                    if (success) {
                                                        model.getServerManager().addChallenge(new ServerConnection.ResponseChallenge(player.getPlayerName(), -1,model.getGameAssets().getServerId() ,ServerConnection.ChallengeStatus.CHALLENGE_SENT));
                                                        player.setStatus("Sent");
                                                        Platform.runLater(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                updateTable();
                                                            }
                                                        });
                                                    }
                                                }
                                            }, player.getPlayerName(), model.getGameAssets().getServerId()));
                                        });
                                    }


                                    setGraphic(btn);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };
        //
        // END
        //

        challengers.setCellValueFactory(new PropertyValueFactory<ControllerTableSetting.Setting, String>("playerName"));
        status.setCellValueFactory(new PropertyValueFactory<ControllerTableSetting.Setting, String>("status"));
        buttons.setCellFactory(cellFactory);

        challengeTable.getColumns().addAll(challengers, status, buttons);
    }

    private void updateTable() {
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
                for (ChallengeablePlayer cPlayer: challengeablePlayers){
                    if (cPlayer.playerName.equals(player)){
                        found = true;
                    }
                }
                for (ServerConnection.ResponseChallenge challenge: model.getChallenges()){
                    if (challenge.getChallenger().equals(player)){
                        found = true;
                    }
                }
                if (!found){
                    challengeablePlayers.add(new ChallengeablePlayer(player, "Unchallenged"));
                }
            }
            }


            //challengeTable.getItems().removeAll(challengeTable.getItems());
            challengeTable.setItems(challengeablePlayers);
        }
    }

    @FXML
    private void onBack(){
        model.getControllerSelectedGame().setMainMenuButtons();
    }

    public static class ChallengeablePlayer {

        private final SimpleStringProperty playerName;
        private final SimpleStringProperty status;

        private ChallengeablePlayer(String key, String value) {
            this.playerName = new SimpleStringProperty(key);
            this.status = new SimpleStringProperty(value);
        }

        public String getPlayerName() {
            return playerName.get();
        }

        public void setPlayerName(String key) {
            this.playerName.set(key);
        }

        public String getStatus() {
            return status.get();
        }

        public void setStatus(String value) {
            this.status.set(value);
        }
    }
}
