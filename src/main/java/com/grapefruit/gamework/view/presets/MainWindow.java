package com.grapefruit.gamework.view.presets;

import com.grapefruit.gamework.view.FXMLRegistry;
import com.grapefruit.gamework.view.IPresetWrapper;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

import java.util.HashMap;

public class MainWindow implements IPresetWrapper {

    private Parent mainWindow = FXMLRegistry.buildMainWindow();

    @Override
    public Parent getParent() {
        return mainWindow;
    }

    @Override
    public void update() {

    }
}
