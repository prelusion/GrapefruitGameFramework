package com.grapefruit.gamework.app.model;

import com.grapefruit.gamework.app.resources.AppSettings;

public class ModelConfigSetting implements IModel {

    public ModelSettingsWindow settingsWindow;

    public ModelConfigSetting(ModelSettingsWindow settingsWindow){
        this.settingsWindow = settingsWindow;
    }

    public ModelSettingsWindow getSettingsWindow() {
        return settingsWindow;
    }
}
