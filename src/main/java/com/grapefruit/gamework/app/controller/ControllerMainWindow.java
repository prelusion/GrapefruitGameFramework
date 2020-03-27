package com.grapefruit.gamework.app.controller;

import com.grapefruit.gamework.app.games.Game;
import com.grapefruit.gamework.app.model.IModel;
import com.grapefruit.gamework.app.model.ModelGameTile;
import com.grapefruit.gamework.app.model.ModelMainWindow;
import com.grapefruit.gamework.app.model.ModelSettingsWindow;
import com.grapefruit.gamework.app.resources.AppSettings;
import com.grapefruit.gamework.app.view.templates.GameTile.GameTileFactory;
import com.grapefruit.gamework.app.view.templates.SettingsWindow.SettingsWindowFactory;
import com.grapefruit.gamework.app.view.templates.SettingsWindow.TemplateSettingsWindow;
import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SelectionModel;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControllerMainWindow implements IController {

    private ModelMainWindow modelMainWindow= null;
    private AppSettings.Server selectedServer;

    @FXML
    private URL location;

    @FXML
    private ComboBox serverSelection;

    @FXML
    private ComboBox userSelection;

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
        for (Game game: modelMainWindow.getGames()){
            ModelGameTile tile = new ModelGameTile(game, modelMainWindow);
            gamesVBox.getChildren().add(GameTileFactory.build(tile).getParent());
        }

        if (modelMainWindow.getSelectedGame() != null){
            
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setModel(IModel model) {
        modelMainWindow = (ModelMainWindow) model;
        initialize();
        updateSelectionBoxes();
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
    public void updateSelectionBoxes(){
        ArrayList<AppSettings.Server> servers =  AppSettings.getSettings().getServers();
        ArrayList<AppSettings.User> users =  AppSettings.getSettings().getUsers();

        serverSelection.getItems().removeAll(serverSelection.getItems());

        for (AppSettings.Server server: servers){
            serverSelection.getItems().add(server);
        }

        userSelection.getItems().removeAll(userSelection.getItems());

        for (AppSettings.User user: users){
            userSelection.getItems().add(new Text(user.toString()));
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
}
