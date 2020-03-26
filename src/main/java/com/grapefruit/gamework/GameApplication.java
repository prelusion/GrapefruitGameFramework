package com.grapefruit.gamework;

import com.grapefruit.gamework.model.ModelMainWindow;
import com.grapefruit.gamework.view.ImageRegistry;
import com.grapefruit.gamework.view.templates.MainWindow.MainWindowFactory;
import com.grapefruit.gamework.view.templates.MainWindow.TemplateMainWindow;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class GameApplication extends Application {


    private ModelMainWindow modelMainWindow;

    public void startApplication(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        modelMainWindow = new ModelMainWindow();

        primaryStage.setTitle("Grapefruit Gamework");
        primaryStage.setScene(new Scene(MainWindowFactory.build(modelMainWindow).getParent(), 1000, 600));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.getIcons().add(ImageRegistry.GAMEWORK_ICON);
        primaryStage.show();
    }
}
