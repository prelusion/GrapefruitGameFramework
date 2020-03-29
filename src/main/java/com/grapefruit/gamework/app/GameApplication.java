package com.grapefruit.gamework.app;

import com.grapefruit.gamework.app.model.ModelGame;
import com.grapefruit.gamework.app.model.ModelGameTile;
import com.grapefruit.gamework.app.model.ModelMainWindow;
import com.grapefruit.gamework.app.resources.ImageRegistry;
import com.grapefruit.gamework.app.view.templates.Game.GameFactory;
import com.grapefruit.gamework.app.view.templates.GameTile.GameTileFactory;
import com.grapefruit.gamework.app.view.templates.MainWindow.MainWindowFactory;
import com.grapefruit.gamework.framework.Game;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class GameApplication extends Application {


    private ModelMainWindow modelMainWindow;
    private static Stage primaryStage;
    private static int WINDOW_START_HEIGHT = 600;
    private static int WINDOW_START_WIDTH = 1000;

    public void startApplication(String[] args){
        launch(args);
    }

    /**
     *
     * @param primaryStage
     *
     * Starts application.
     */
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        openLauncher();
    }

    public static Stage getStage(){
        return primaryStage;
    }

    public static void playGame(Game game){
        primaryStage.setScene(new Scene(GameFactory.build(new ModelGame(game)).getParent(), 1100, 800));
        primaryStage.setTitle("Now playing: " + game.getName());
        primaryStage.show();
    }

    public void openLauncher(){
        modelMainWindow = new ModelMainWindow();
        primaryStage.setTitle("Grapefruit Gamework");
        primaryStage.setScene(new Scene(MainWindowFactory.build(modelMainWindow).getParent(), WINDOW_START_WIDTH, WINDOW_START_HEIGHT));
        //primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.getIcons().add(ImageRegistry.GAMEWORK_ICON);
        primaryStage.show();
    }
}
