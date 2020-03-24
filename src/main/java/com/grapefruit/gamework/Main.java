package com.grapefruit.gamework;

import com.grapefruit.gamework.view.FXMLRegistry;
import com.grapefruit.gamework.view.presets.MainWindow;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Grapefruit Gamework");
        //Parent root = new MainWindow().getParent();
        Parent root = FXMLRegistry.buildMainWindow();

        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.show();
    }
}
