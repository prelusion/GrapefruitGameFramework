package com.grapefruit.gamework.app.controller;

import com.grapefruit.gamework.app.model.IModel;
import com.grapefruit.gamework.app.model.ModelConfigSetting;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * The type Controller config setting.
 */
public class ControllerConfigSetting implements IController {

    private ModelConfigSetting modelConfigSetting;

    @FXML
    private TextField serverTimeout;

    @FXML
    private TextField serverPort;

    @Override
    public void setModel(IModel model) {
        this.modelConfigSetting = (ModelConfigSetting) model;
        serverTimeout.setText(String.valueOf(this.modelConfigSetting.getSettingsWindow().getLocalSettings().getTimeout()));
        serverPort.setText(String.valueOf(this.modelConfigSetting.getSettingsWindow().getLocalSettings().getPort()));

        serverPort.textProperty().addListener((observable, oldValue, newValue) -> {
            Platform.runLater(() -> {
                try {
                    if (!newValue.matches("\\d*")) {
                        serverPort.setText(newValue.replaceAll("[^\\d]", ""));
                    } else {
                        if (Integer.parseInt(newValue) > 65535 && newValue.length() > 5) {
                            Platform.runLater(() -> {
                                serverPort.setText(oldValue);
                            });
                        } else {
                            this.modelConfigSetting.getSettingsWindow().getLocalSettings().setPort(Integer.valueOf(serverPort.getText()));
                        }
                    }
                } catch (NumberFormatException e) {
                    serverPort.setText("");
                }
            });
        });
        serverTimeout.textProperty().addListener((observable, oldValue, newValue) -> {
            Platform.runLater(() -> {
                Platform.runLater(() -> {
                    if (!newValue.matches("\\d*")) {
                        serverTimeout.setText(newValue.replaceAll("[^\\d]", ""));
                    } else {
                        if (newValue.length() > 8) {
                            Platform.runLater(() -> {
                                serverTimeout.setText(oldValue);
                            });
                        } else {
                            this.modelConfigSetting.getSettingsWindow().getLocalSettings().setTimeout(Integer.valueOf(serverTimeout.getText()));
                        }
                    }
                });
            });
        });
    }

    /**
     * Initialie.
     */
    @FXML
    public void initialie() {
    }

}
