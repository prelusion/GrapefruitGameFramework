package com.grapefruit.gamework.app.model;


import com.grapefruit.gamework.app.resources.AppSettings;

import java.util.HashMap;

/**
 * The type Model table setting.
 */
public class ModelTableSetting implements IModel {

    /**
     * The enum Table setting preset.
     */
    public enum TableSettingPreset {
        /**
         * Servers table setting preset.
         */
        SERVERS,
        /**
         * Users table setting preset.
         */
        USERS
    }

    private String keyName;
    private String valueName;
    private HashMap<String, String> rows;
    private ModelSettingsWindow modelSettingsWindow;
    private TableSettingPreset preset;

    /**
     * Instantiates a new Model table setting.
     *
     * @param rows                the rows
     * @param keyName             the key name
     * @param valueName           the value name
     * @param modelSettingsWindow the model settings window
     * @param preset              the preset
     */
    public ModelTableSetting(HashMap<String, String> rows, String keyName, String valueName, ModelSettingsWindow modelSettingsWindow, TableSettingPreset preset) {
        this.rows = rows;
        this.keyName = keyName;
        this.valueName = valueName;
        this.modelSettingsWindow = modelSettingsWindow;
        this.preset = preset;
    }

    /**
     * Gets preset.
     *
     * @return current preset
     */
    public TableSettingPreset getPreset() {
        return preset;
    }

    /**
     * Gets settings.
     *
     * @return Settings settings
     */
    public AppSettings.Settings getSettings() {
        return modelSettingsWindow.getLocalSettings();
    }

    /**
     * Gets key name.
     *
     * @return Setting 's key name.
     */
    public String getKeyName() {
        return keyName;
    }

    /**
     * Sets key name.
     *
     * @param keyName Sets setting's key name
     */
    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    /**
     * Gets value name.
     *
     * @return setting 's value name.
     */
    public String getValueName() {
        return valueName;
    }

    /**
     * Sets value name.
     *
     * @param valueName Returns setting's value name.
     */
    public void setValueName(String valueName) {
        this.valueName = valueName;
    }

    /**
     * Sets rows.
     *
     * @param rows returns  HashMap of setting's key & value pairs.
     */
    public void setRows(HashMap<String, String> rows) {
        this.rows = rows;
    }
}
