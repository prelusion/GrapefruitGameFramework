package com.grapefruit.gamework;

import com.grapefruit.gamework.view.presets.ImageRegistry;
import com.grapefruit.gamework.view.presets.MainWindow;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class GameApplication extends Application {

    public void startApplication(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Grapefruit Gamework");
        primaryStage.setScene(new Scene(new MainWindow().getParent(), 1000, 600));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.getIcons().add(ImageRegistry.GAMEWORK_ICON);
        primaryStage.show();
    }
}
