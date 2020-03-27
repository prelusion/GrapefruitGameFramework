package com.grapefruit.gamework.app.controller;

import com.grapefruit.gamework.app.games.Game;
import com.grapefruit.gamework.app.model.IModel;
import com.grapefruit.gamework.app.model.ModelGameTile;
import com.grapefruit.gamework.app.model.ModelSettingsWindow;
import com.grapefruit.gamework.app.model.ModelTableSetting;
import com.grapefruit.gamework.app.resources.AppSettings;
import com.grapefruit.gamework.app.view.templates.GameTile.GameTileFactory;
import com.grapefruit.gamework.app.view.templates.TableSetting.TableSettingFactory;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class ControllerSettingsWindow implements IController {

    private ModelSettingsWindow modelSettingsWindow= null;

    private AppSettings.Settings localSettings;

    @FXML
    private Button cancelButton;

    @FXML
    private Button doneButton;

    @FXML
    private Button applyButton;

    @FXML
    private URL location;

    @FXML
    private FlowPane selectedSetting;

    @FXML
    private ResourceBundle resources;

    public ControllerSettingsWindow()
    {

    }

    private void initialize()
    {


    }

    private void setSelectedSetting(Parent parent){
        localSettings = AppSettings.getSettings();
        selectedSetting.getChildren().removeAll(selectedSetting.getChildren());
        selectedSetting.getChildren().add(parent);
    }

    @Override
    public void setModel(IModel model) {
        modelSettingsWindow = (ModelSettingsWindow) model;
        initialize();
        localSettings = AppSettings.getSettings();
        onClickServer();
    }

    @FXML
    private void onClickServer(){

        HashMap<String, String> servers = new HashMap<>();
        for (AppSettings.Server server: localSettings.getServers()){
            servers.put(server.getName(), server.getIp());
        }
        setSelectedSetting(TableSettingFactory.build(new ModelTableSetting(servers, "Server name", "Server address", modelSettingsWindow, ModelTableSetting.TableSettingPreset.SERVERS)).getParent());
    }

    @FXML
    private void onCancelButton(){
        Stage stage = (Stage )cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void onApplyButton(){
        modelSettingsWindow.saveSettings();
        onClickServer();
    }

    @FXML
    private void onDoneButton(){
        modelSettingsWindow.saveSettings();
        Stage stage = (Stage )doneButton.getScene().getWindow();
        stage.close();
    }
}
