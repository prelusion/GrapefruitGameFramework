package com.grapefruit.gamework.app;

import com.grapefruit.gamework.app.model.ModelGame;
import com.grapefruit.gamework.app.model.ModelGameTile;
import com.grapefruit.gamework.app.model.ModelMainWindow;
import com.grapefruit.gamework.app.resources.ImageRegistry;
import com.grapefruit.gamework.app.view.templates.Game.GameFactory;
import com.grapefruit.gamework.app.view.templates.GameTile.GameTileFactory;
import com.grapefruit.gamework.app.view.templates.MainWindow.MainWindowFactory;
import com.grapefruit.gamework.framework.Assets;
import com.grapefruit.gamework.framework.Game;
import com.grapefruit.gamework.framework.GameWrapper;
import com.grapefruit.gamework.framework.player.Player;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.List;

public class GameApplication extends Application {


    private static ModelMainWindow modelMainWindow;
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

    public static void playGame(Assets assets, Game game, Player[] localPlayers){
        primaryStage.setScene(new Scene(GameFactory.build(new ModelGame(game, assets, localPlayers)).getParent(), 1100, 800));
        primaryStage.setTitle("Now playing: " + assets.getDisplayName());
        primaryStage.show();
    }

    public static void openLauncher(){
        modelMainWindow = new ModelMainWindow();
        primaryStage.setTitle("Grapefruit Gamework");
        primaryStage.setScene(new Scene(MainWindowFactory.build(modelMainWindow).getParent(), WINDOW_START_WIDTH, WINDOW_START_HEIGHT));
        //primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.getIcons().add(ImageRegistry.GAMEWORK_ICON);
        primaryStage.show();
    }
}
