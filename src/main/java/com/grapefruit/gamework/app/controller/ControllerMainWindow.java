package com.grapefruit.gamework.app.controller;

import com.grapefruit.gamework.app.model.*;
import com.grapefruit.gamework.app.resources.AppSettings;
import com.grapefruit.gamework.app.view.templates.GameTile.GameTileFactory;
import com.grapefruit.gamework.app.view.templates.SelectedGame.SelectedGameFactory;
import com.grapefruit.gamework.app.view.templates.SettingsWindow.SettingsWindowFactory;
import com.grapefruit.gamework.app.view.templates.SettingsWindow.TemplateSettingsWindow;
import com.grapefruit.gamework.framework.GameWrapper;
import com.grapefruit.gamework.network.CommandCallback;
import com.grapefruit.gamework.network.Commands;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.apache.commons.validator.routines.InetAddressValidator;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The type Controller main window.
 */
public class ControllerMainWindow implements IController {

    private ModelMainWindow modelMainWindow = null;
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
    public ControllerMainWindow() {
    }

    /**
     * Required for FXML
     * also updates list of games.
     */
    private void initialize() {
        updateGames();
    }

    /**
     * Updates list of games displayed on left side.
     */
    private void updateGames() {
        gamesVBox.getChildren().removeAll(gamesVBox.getChildren());
        for (GameWrapper gameWrapper : modelMainWindow.getGames()) {
            ModelGameTile tile = new ModelGameTile(gameWrapper, modelMainWindow);
            gamesVBox.getChildren().add(GameTileFactory.build(tile).getParent());
        }

        if (modelMainWindow.getSelectedGame() != null) {
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
        if (this.modelMainWindow.getServerManager() != null) {
            if (this.modelMainWindow.getServerManager().connected.getValue()) {
                onConnected();
            } else {
                onDisconnected();
            }
            if (this.modelMainWindow.getServerManager().getConnectedName() != null) {
                userName.setText(this.modelMainWindow.getServerManager().getConnectedName());
            }
        } else {
            onDisconnected();
        }
    }


    /**
     * Called when settings button is clicked.
     * Open settings window.
     */
    @FXML
    private void onSettingsClicked() {
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
    private void onClick(MouseEvent event) {
        if (event.getTarget() instanceof Node) {
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
    public void updateSelectionBox() {
        ArrayList<AppSettings.Server> servers = AppSettings.getSettings().getServers();
        serverSelection.getItems().removeAll(serverSelection.getItems());
        for (AppSettings.Server server : servers) {
            serverSelection.getItems().add(server);
        }
        if (AppSettings.getSettings().getDefaultServer() != null) {
            serverSelection.getSelectionModel().select(AppSettings.getSettings().getDefaultServer());
            selectedServer = AppSettings.getSettings().getDefaultServer();
        }
        if (AppSettings.getSettings().getDefaultUser() != null) {
            userName.setText(AppSettings.getSettings().getDefaultUser().getUsername());
        }

    }

    /**
     * Called when selected server is changed in ComboBox.
     */
    @FXML
    private void onServerSelectionChange() {
        if (serverSelection.getValue() instanceof AppSettings.Server) {
            selectedServer = (AppSettings.Server) serverSelection.getValue();
        }
    }

    @FXML
    private void onConnect() {
        InetAddressValidator validator = new InetAddressValidator();
        if (selectedServer != null && validator.isValid(selectedServer.getIp()) && modelMainWindow.getServerManager() != null) {
            setDefaultSettings();
            serverSelection.setDisable(true);
            userName.setDisable(true);
            connectButton.setDisable(true);
            connectionStatus.setText("Connecting...");
            modelMainWindow.getServerManager().setConnectedName(userName.getText());

            new Thread(new Runnable() {
                @Override
                public void run() {
                    if (modelMainWindow.getServerManager().connect(selectedServer.getIp())) {
                        AtomicBoolean loggedIn = new AtomicBoolean(false);

                        new Thread(() -> {
                            try {
                                Thread.sleep(3000);
                                if (!loggedIn.get()) {
                                    modelMainWindow.getServerManager().disconnect();
                                    Platform.runLater(() -> {
                                        onDisconnected();
                                        connectionStatus.setText("Failed to login");
                                    });
                                }
                            } catch (InterruptedException ignored) {}
                        }).start();

                        modelMainWindow.getServerManager().queueCommand(Commands.login(userName.getText(), (success, args) -> {
                            if (args != null) {
                                for (String arg : args) System.out.println(arg);
                            }

                            if (success) {

                                loggedIn.set(true);
                                Platform.runLater(() -> {
                                    onConnected();
                                    modelMainWindow.getServerManager().connected.setValue(true);
                                });
                            } else {
                                loggedIn.set(true);
                                Platform.runLater(() -> {
                                    onDisconnected();
                                    if (args != null) {
                                        connectionStatus.setText(args[0]);
                                    }
                                });

                            }

                        }));
                    } else {
                        Platform.runLater(() -> {
                            onDisconnected();
                            connectionStatus.setText("Server offline");
                        });
                    }
                }
            }).start();


        } else {
            connectionStatus.setText("Invalid");
        }
    }

    private void onConnected() {
        if (modelMainWindow.getServerManager() != null) {
            connectionStatus.setText("Connected");
            connectionStatus.setFill(Color.GREEN);
            connectButton.setText("Disconnect");
            serverSelection.setDisable(true);
            userName.setDisable(true);
            connectButton.setDisable(false);
            connectButton.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    connectButton.setText("Disconnecting...");
                    connectionStatus.setFill(Color.BLACK);
                    modelMainWindow.getServerManager().connected.setValue(false);

                    new Thread(() -> {
                        modelMainWindow.getServerManager().disconnect();
                        Platform.runLater(() -> {
                            onDisconnected();
                        });
                    }).start();

                }
            });
            modelMainWindow.getServerManager().queueCommand(Commands.getGameList(new CommandCallback() {
                @Override
                public void onResponse(boolean success, String[] args) {
                    if (success && args != null && args.length > 0) {
                        setAvailableGames(args);
                    }

                }
            }));
        }
    }

    private void onDisconnected() {
        connectButton.setText("Connect");
        connectButton.setDisable(false);
        connectionStatus.setText("Disconnected");
        connectionStatus.setFill(Color.RED);
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


    /**
     * Get available games string [ ].
     *
     * @return the string [ ]
     */
    public String[] getAvailableGames() {
        return availableGames;
    }

    /**
     * Sets available games.
     *
     * @param games the games
     */
    public synchronized void setAvailableGames(String[] games) {
        availableGames = games;
        modelMainWindow.setAvailableGames(games);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                updateGames();
            }
        });
    }

    /**
     * Sets default settings.
     */
    public void setDefaultSettings() {
        AppSettings.Settings settings = AppSettings.getSettings();
        settings.setDefaultServer(selectedServer);
        AppSettings.User defaultUser = new AppSettings.User();
        defaultUser.setUsername(userName.getText());
        settings.setDefaultUser(defaultUser);
        AppSettings.saveSettings(settings);
    }
}
