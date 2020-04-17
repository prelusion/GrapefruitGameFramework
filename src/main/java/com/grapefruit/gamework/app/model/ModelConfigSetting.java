package com.grapefruit.gamework.app.model;

/**
 * The type Model config setting.
 */
public class ModelConfigSetting implements IModel {

    /**
     * The Settings window.
     */
    public ModelSettingsWindow settingsWindow;

    /**
     * Instantiates a new Model config setting.
     *
     * @param settingsWindow the settings window
     */
    public ModelConfigSetting(ModelSettingsWindow settingsWindow) {
        this.settingsWindow = settingsWindow;
    }

    /**
     * Gets settings window.
     *
     * @return the settings window
     */
    public ModelSettingsWindow getSettingsWindow() {
        return settingsWindow;
    }
}
