package com.grapefruit.example.observable;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        Player leon = new Player("Leon", false);

        Button btn = new Button();
        btn.setText("Mijn naam is: " + leon.getName());

        leon.getNameProperty().addListener(
                (observable, oldValue, newValue) -> btn.setText("Mijn naam is: " + leon.getName())
        );

        btn.setOnAction(new EventHandler<ActionEvent>() {
            boolean toggle = false;

            @Override
            public void handle(ActionEvent event) {
                leon.setName(toggle ? "Leon" : "Janus");
                toggle = !toggle;
            }
        });

        StackPane root = new StackPane();
        root.getChildren().add(btn);
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
    }
}
