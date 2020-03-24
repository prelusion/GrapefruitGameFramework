package com.grapefruit.gamework.view.presets;

import com.grapefruit.gamework.view.FXMLRegistry;
import com.grapefruit.gamework.view.IPresetWrapper;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;

import java.util.HashMap;

public class MainWindow implements IPresetWrapper {

    private Parent mainWindow = FXMLRegistry.buildMainWindow();

    private ScrollPane scrollPane = (ScrollPane) mainWindow.getScene().lookup("#game-scroll-pane");

    @Override
    public Parent getParent() {
        return mainWindow;
    }

    @Override
    public void init() {
        for (int i = 0; i < 2; i++){
            continue;
        }
    }
}
