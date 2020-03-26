package com.grapefruit.gamework.app.controller;

import com.grapefruit.gamework.app.games.Game;
import com.grapefruit.gamework.app.model.IModel;
import com.grapefruit.gamework.app.model.ModelGameTile;
import com.grapefruit.gamework.app.model.ModelSettingsWindow;
import com.grapefruit.gamework.app.model.ModelTableSetting;
import com.grapefruit.gamework.app.resources.AppSettings;
import com.grapefruit.gamework.app.view.templates.GameTile.GameTileFactory;
import com.grapefruit.gamework.app.view.templates.TableSetting.TableSettingFactory;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerSettingsWindow implements IController {

    private ModelSettingsWindow modelSettingsWindow= null;

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
        selectedSetting.getChildren().removeAll(selectedSetting.getChildren());
        selectedSetting.getChildren().add(parent);
    }

    @Override
    public void setModel(IModel model) {
        modelSettingsWindow = (ModelSettingsWindow) model;
        initialize();
    }

    @FXML
    private void onClickServer(){
        setSelectedSetting(TableSettingFactory.build(new ModelTableSetting(AppSettings.getServers(), "Server name", "Server IP and Port")).getParent());
    }

}
