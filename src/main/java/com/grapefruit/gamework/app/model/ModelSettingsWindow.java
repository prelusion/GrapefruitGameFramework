package com.grapefruit.gamework.app.model;

import com.grapefruit.gamework.app.controller.ControllerMainWindow;
import com.grapefruit.gamework.app.resources.AppSettings;

/**
 * The type Model settings window.
 */
public class ModelSettingsWindow implements IModel {

    /**
     * The Local settings.
     */
    AppSettings.Settings localSettings;
    /**
     * The Main window.
     */
    ModelMainWindow mainWindow;
    /**
     * The Controller main window.
     */
    ControllerMainWindow controllerMainWindow;

    /**
     * Instantiates a new Model settings window.
     *
     * @param mainWindow           the main window
     * @param controllerMainWindow the controller main window
     */
    public ModelSettingsWindow(ModelMainWindow mainWindow, ControllerMainWindow controllerMainWindow) {
        localSettings = AppSettings.getSettings();
        this.mainWindow = mainWindow;
        this.controllerMainWindow = controllerMainWindow;
    }

    /**
     * Gets local settings.
     *
     * @return gets locally stored settings
     */
    public AppSettings.Settings getLocalSettings() {
        return localSettings;
    }

    /**
     * Saves locally stored settings to home folder.
     */
    public void saveSettings() {
        AppSettings.saveSettings(localSettings);
        controllerMainWindow.updateSelectionBox();
    }

}
