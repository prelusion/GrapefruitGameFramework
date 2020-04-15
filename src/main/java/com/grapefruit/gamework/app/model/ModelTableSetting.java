package com.grapefruit.gamework.app.model;


import com.grapefruit.gamework.app.resources.AppSettings;

import java.util.HashMap;

public class ModelTableSetting implements IModel {

    public enum TableSettingPreset {
        SERVERS,
        USERS
    }

    private String keyName;
    private String valueName;
    private HashMap<String, String> rows;
    private ModelSettingsWindow modelSettingsWindow;
    private TableSettingPreset preset;

    public ModelTableSetting(HashMap<String, String> rows, String keyName, String valueName, ModelSettingsWindow modelSettingsWindow, TableSettingPreset preset) {
        this.rows = rows;
        this.keyName = keyName;
        this.valueName = valueName;
        this.modelSettingsWindow = modelSettingsWindow;
        this.preset = preset;
    }

    /**
     * @return current preset
     */
    public TableSettingPreset getPreset() {
        return preset;
    }

    /**
     * @return Settings
     */
    public AppSettings.Settings getSettings() {
        return modelSettingsWindow.getLocalSettings();
    }

    /**
     * @return Setting's key name.
     */
    public String getKeyName() {
        return keyName;
    }

    /**
     * @param keyName Sets setting's key name
     */
    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    /**
     * @return setting's value name.
     */
    public String getValueName() {
        return valueName;
    }

    /**
     * @param valueName Returns setting's value name.
     */
    public void setValueName(String valueName) {
        this.valueName = valueName;
    }

    /**
     * @param rows returns  HashMap of setting's key & value pairs.
     */
    public void setRows(HashMap<String, String> rows) {
        this.rows = rows;
    }
}
