package com.grapefruit.gamework.app.model;

import com.grapefruit.gamework.app.controller.ControllerSettingsWindow;
import com.grapefruit.gamework.app.games.Game;
import com.grapefruit.gamework.app.resources.AppSettings;

import java.util.ArrayList;
import java.util.HashMap;

public class ModelTableSetting implements IModel {

    public enum TableSettingPreset{
        SERVERS,
        USERS
    }

    private String keyName;
    private String valueName;
    private HashMap<String, String> rows;
    private ModelSettingsWindow modelSettingsWindow;
    private TableSettingPreset preset;

    public ModelTableSetting(HashMap<String, String> rows, String keyName, String valueName, ModelSettingsWindow modelSettingsWindow, TableSettingPreset preset){
        this.rows = rows;
        this.keyName = keyName;
        this.valueName = valueName;
        this.modelSettingsWindow = modelSettingsWindow;
        this.preset = preset;
    }

    public TableSettingPreset getPreset() {
        return preset;
    }

    public AppSettings.Settings getSettings(){
        return modelSettingsWindow.getLocalSettings();
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public String getValueName() {
        return valueName;
    }

    public void setValueName(String valueName) {
        this.valueName = valueName;
    }

    public void setRows(HashMap<String, String> rows) {
        this.rows = rows;
    }
}
