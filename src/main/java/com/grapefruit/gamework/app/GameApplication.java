package com.grapefruit.gamework.app;

import com.grapefruit.gamework.app.model.ModelGame;
import com.grapefruit.gamework.app.model.ModelMainWindow;
import com.grapefruit.gamework.app.resources.ImageRegistry;
import com.grapefruit.gamework.app.view.templates.Game.GameFactory;
import com.grapefruit.gamework.app.view.templates.MainWindow.MainWindowFactory;
import com.grapefruit.gamework.app.view.templates.Template;
import com.grapefruit.gamework.framework.Assets;
import com.grapefruit.gamework.framework.Game;
import com.grapefruit.gamework.framework.network.ServerManager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameApplication extends Application {
    
    private static Stage primaryStage;
    private static final int WINDOW_START_HEIGHT = 600;
    private static final int WINDOW_START_WIDTH = 1000;
    private static final int GAME_SCENE_WIDTH = 1100;
    private static final int GAME_SCENE_HEIGHT = 800;
    private static final ServerManager SERVER_MANAGER = new ServerManager();

    public void startApplication(String[] args) {
        launch(args);
    }

    /**
     * @param primaryStage Starts application.
     */
    @Override
    public void start(Stage primaryStage) {
        GameApplication.primaryStage = primaryStage;
        primaryStage.getIcons().add(ImageRegistry.GAMEWORK_ICON);
        openLauncher();
    }

    public static Stage getStage() {
        return primaryStage;
    }

    public static void openLauncher() {
        ModelMainWindow modelMainWindow = new ModelMainWindow(SERVER_MANAGER);
        Template template = MainWindowFactory.build(modelMainWindow);
        Scene scene = new Scene(template.getParent(), WINDOW_START_WIDTH, WINDOW_START_HEIGHT);

        setScene(scene);
    }

    public static void startOfflineGame(Assets assets, Game game) {
        startGame(assets, game, null, false);
    }

    public static void startOnlineGame(Assets assets, Game game, ServerManager serverManager) {
        startGame(assets, game, serverManager, false);
    }

    public static void startTournamentGame(Assets assets, Game game, ServerManager serverManager) {
        startGame(assets, game, serverManager, true);
    }

    private static void startGame(Assets assets, Game game, ServerManager serverManager, boolean isTournament) {
        ModelGame modelGame = new ModelGame(game, assets, serverManager, isTournament);
        Template template = GameFactory.build(modelGame);
        Scene scene = new Scene(template.getParent(), GAME_SCENE_WIDTH, GAME_SCENE_HEIGHT);

        setScene(scene, "Now playing: " + assets.getDisplayName());
    }

    private static void setScene(Scene scene) {
        setScene(scene, "Grapefruit Gamework");
    }

    private static void setScene(Scene scene, String title) {
        primaryStage.setScene(scene);
        primaryStage.setTitle(title);
        primaryStage.show();
    }
}
