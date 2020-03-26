package com.grapefruit.gamework.app.model;

import com.grapefruit.gamework.app.resources.AppSettings;

public class ModelSettingsWindow implements IModel {

    public ModelSettingsWindow(){

    }

    public AppSettings.Settings getSettings(){
        return AppSettings.getSettings();
    }


}
