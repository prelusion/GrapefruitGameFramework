package com.grapefruit.gamework.view.templates;

import com.grapefruit.gamework.view.IPresetWrapper;
import javafx.scene.Parent;

public class MainWindow implements IPresetWrapper {

    private Parent mainWindow;

    @Override
    public Parent getParent() {
        return mainWindow;
    }

    @Override
    public void update() {
    }
}
