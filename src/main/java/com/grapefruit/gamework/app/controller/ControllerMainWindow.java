package com.grapefruit.gamework.app.controller;

import com.grapefruit.gamework.app.model.*;
import com.grapefruit.gamework.app.resources.AppSettings;
import com.grapefruit.gamework.app.view.templates.GameTile.GameTileFactory;
import com.grapefruit.gamework.app.view.templates.SelectedGame.SelectedGameFactory;
import com.grapefruit.gamework.app.view.templates.SettingsWindow.SettingsWindowFactory;
import com.grapefruit.gamework.app.view.templates.SettingsWindow.TemplateSettingsWindow;
import com.grapefruit.gamework.framework.GameWrapper;
import com.grapefruit.gamework.framework.network.CommandCallback;
import com.grapefruit.gamework.framework.network.Commands;
import com.grapefruit.gamework.framework.network.ServerManager;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.apache.commons.validator.routines.InetAddressValidator;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControllerMainWindow implements IController {

    private ModelMainWindow modelMainWindow= null;
    private AppSettings.Server selectedServer;
    private String[] availableGames;

    @FXML
    private URL location;

    @FXML
    private Button connectButton;

    @FXML
    private Text connectionStatus;

    @FXML
    private HBox selectedGame;

    @FXML
    private ComboBox serverSelection;

    @FXML
    private TextField userName;

    @FXML
    private VBox parent;

    @FXML
    private VBox gamesVBox;

    @FXML
    private Button settingsButton;

    @FXML
    private ResourceBundle resources;

    /**
     * Required for FXML
     */
    public ControllerMainWindow()
    {
    }

    /**
     * Required for FXML
     * also updates list of games.
     */
    private void initialize()
    {
        updateGames();
    }

    /**
     * Updates list of games displayed on left side.
     */
    private void updateGames(){
        gamesVBox.getChildren().removeAll(gamesVBox.getChildren());
        for (GameWrapper gameWrapper: modelMainWindow.getGames()){
            ModelGameTile tile = new ModelGameTile(gameWrapper, modelMainWindow);
            gamesVBox.getChildren().add(GameTileFactory.build(tile).getParent());
        }

        if (modelMainWindow.getSelectedGame() != null){
            selectedGame.getChildren().removeAll(selectedGame.getChildren());
            boolean online = false;
            if (modelMainWindow.getAvailableGames() != null) {
                for (String game : modelMainWindow.getAvailableGames()) {
                    if (game.equals(modelMainWindow.getSelectedGame().getAssets().getServerId())) {
                        online = true;
                    }
                }
            }
            selectedGame.getChildren().add(SelectedGameFactory.build(new ModelSelectedGame(modelMainWindow.getSelectedGame(), modelMainWindow.getServerManager(), userName.getText(), online)).getParent());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setModel(IModel model) {
        modelMainWindow = (ModelMainWindow) model;
        initialize();
        updateSelectionBox();
        if (this.modelMainWindow.getServerManager() != null){
            if (this.modelMainWindow.getServerManager().connected.getValue()) {
                connectionStatus.setText("Connected");
            } else {
                connectionStatus.setText("Disconnected");
            }
        } else {
            connectionStatus.setText("Disconnected");
        }
    }


    /**
     * Called when settings button is clicked.
     * Open settings window.
     */
    @FXML
    private void onSettingsClicked(){
        TemplateSettingsWindow settingsWindow = (TemplateSettingsWindow) SettingsWindowFactory.build(new ModelSettingsWindow(modelMainWindow, this));
        settingsWindow.getParent();
    }

    /**
     * Called when mouse is clicked on main window.
     * Used to determine if a GameTile was clicked.
     *
     * @param event supplied by FXML
     */
    @FXML
    private void onClick(MouseEvent event){
        if (event.getTarget() instanceof Node){
            Node node = (Node) event.getTarget();
            if (node != null) {
                if (node.getId() != null) {
                    if (node.getId().equals("gameTile")) {
                        updateGames();
                    }
                }
                if (node.getParent() != null) {
                    if (node.getParent().getId() != null) {
                        if (node.getParent().getId().equals("gameTile")) {
                            updateGames();
                        }
                    }
                }
            }
        }
    }

    /**
     * Updates selection boxes by fetching settings from AppSettings.
     */
    public void updateSelectionBox(){
        ArrayList<AppSettings.Server> servers =  AppSettings.getSettings().getServers();

        serverSelection.getItems().removeAll(serverSelection.getItems());

        for (AppSettings.Server server: servers){
            serverSelection.getItems().add(server);
        }
    }

    /**
     * Called when selected server is changed in ComboBox.
     */
    @FXML
    private void onServerSelectionChange(){
        if (serverSelection.getValue() instanceof AppSettings.Server) {
            selectedServer = (AppSettings.Server) serverSelection.getValue();
        }
    }

    @FXML
    private void onConnect(){
        InetAddressValidator validator = new InetAddressValidator();
        if (selectedServer != null && validator.isValid(selectedServer.getIp()) && modelMainWindow.getServerManager() == null) {
            serverSelection.setDisable(true);
            userName.setDisable(true);
            connectButton.setDisable(true);
            modelMainWindow.setServerManager(new ServerManager());
            connectionStatus.setText("Connecting...");
        } else {
            connectionStatus.setText("Invalid");
        }
        if (modelMainWindow.getServerManager() != null) {
            modelMainWindow.getServerManager().connected.addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if (oldValue != newValue) {
                        if (newValue) {
                            onConnected();
                        } else {
                            onDisconnected();
                        }
                    }
                }
            });
        }
        modelMainWindow.getServerManager().connect(selectedServer.getIp());
        modelMainWindow.getServerManager().queueCommand(Commands.login(userName.getText(), new CommandCallback() {
            @Override
            public void onResponse(boolean success, String[] args) {
            }
        }));
    }

    private void onConnected(){
        if (modelMainWindow.getServerManager() != null) {
            connectionStatus.setText("Connected");
            connectButton.setText("Disconnect");
            connectButton.setDisable(false);
            connectButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    modelMainWindow.getServerManager().disconnect();
                }
            });
            modelMainWindow.getServerManager().queueCommand(Commands.getGameList(new CommandCallback() {
                @Override
                public void onResponse(boolean success, String[] args) {
                    if (success && args != null && args.length > 0)
                        setAvailableGames(args);
                }
            }));
        }
    }

    private void onDisconnected(){
        connectButton.setText("Connect");
        connectionStatus.setText("Disconnected");
        serverSelection.setDisable(false);
        userName.setDisable(false);
        setAvailableGames(new String[0]);
        updateGames();
        connectButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                onConnect();
            }
        });
    }


    public String[] getAvailableGames() {
        return availableGames;
    }

    public synchronized void setAvailableGames(String[] games){
        availableGames = games;
        modelMainWindow.setAvailableGames(games);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                updateGames();
            }
        });
    }
}
