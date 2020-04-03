package com.grapefruit.gamework.app.controller;

import com.grapefruit.gamework.app.GameApplication;
import com.grapefruit.gamework.app.model.IModel;
import com.grapefruit.gamework.app.model.ModelLobbyBrowser;
import com.grapefruit.gamework.framework.network.ServerConnection;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
    }

    private void updateChallenges(){
        TableColumn challengers = new TableColumn("Challengers");
        TableColumn status = new TableColumn("Status");
        TableColumn button = new TableColumn("");

        final ObservableList<Challenge> challenges = FXCollections.observableArrayList();
        for (ServerConnection.ResponseChallenge challenge: model.getChallenges()){

        }

        challengers.setCellValueFactory(new PropertyValueFactory<ControllerTableSetting.Setting,String>("challenger"));
        status.setCellValueFactory(new PropertyValueFactory<ControllerTableSetting.Setting,String>("status"));

        challengeTable.getItems().removeAll(challengeTable.getItems());
        challengeTable.getColumns().removeAll(challengeTable.getColumns());
        challengeTable.setItems(challenges);
        challengeTable.getColumns().addAll(challengers, status);
    }

    @FXML
    private void onBack(){
        model.getControllerSelectedGame().setMainMenuButtons();
    }

    public static class Challenge {

        private final SimpleStringProperty challenger;
        private final SimpleStringProperty status;

        private Challenge(String key, String value) {
            this.challenger = new SimpleStringProperty(key);
            this.status = new SimpleStringProperty(value);
        }

        public String getKey() {
            return challenger.get();
        }

        public void setKey(String key) {
            this.challenger.set(key);
        }

        public String getValue() {
            return status.get();
        }

        public void setValue(String value) {
            this.status.set(value);
        }
    }
}
