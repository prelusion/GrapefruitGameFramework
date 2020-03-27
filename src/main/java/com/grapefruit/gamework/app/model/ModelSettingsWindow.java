package com.grapefruit.gamework.app.model;

import com.grapefruit.gamework.app.controller.ControllerMainWindow;
import com.grapefruit.gamework.app.resources.AppSettings;

public class ModelSettingsWindow implements IModel {

    AppSettings.Settings localSettings;
    ModelMainWindow mainWindow;
    ControllerMainWindow controllerMainWindow;

    public ModelSettingsWindow(ModelMainWindow mainWindow, ControllerMainWindow controllerMainWindow){
        localSettings = AppSettings.getSettings();
        this.mainWindow = mainWindow;
        this.controllerMainWindow = controllerMainWindow;
    }

    /**
     * @return gets locally stored settings
     */
    public AppSettings.Settings getLocalSettings(){
        return localSettings;
    }

    /**
     * Saves locally stored settings to home folder.
     */
    public void saveSettings(){
        AppSettings.saveSettings(localSettings);
        controllerMainWindow.updateSelectionBoxes();
    }

}
