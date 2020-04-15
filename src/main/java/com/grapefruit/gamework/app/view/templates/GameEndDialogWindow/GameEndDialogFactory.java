package com.grapefruit.gamework.app.view.templates.GameEndDialogWindow;

import com.grapefruit.gamework.app.GameApplication;
import com.grapefruit.gamework.app.controller.IController;
import com.grapefruit.gamework.app.model.IModel;
import com.grapefruit.gamework.app.resources.FXMLRegistry;
import com.grapefruit.gamework.app.resources.ResourceLoader;
import com.grapefruit.gamework.app.view.templates.Template;
import com.grapefruit.gamework.app.view.templates.TemplateFactory;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class GameEndDialogFactory implements TemplateFactory {

    private static Stage previousDialog;

    private static GameEndDialogFactory factory = new GameEndDialogFactory();

    public static Template build(IModel model) {
        return factory.buildTemplate(model);
    }

    private GameEndDialogFactory() {
    }

    public static void destroyPreviousDialogIfExists() {
        if (previousDialog != null) previousDialog.close();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Template buildTemplate(IModel model) {
        destroyPreviousDialogIfExists();

        FXMLLoader loader = new ResourceLoader().getViewLoader(FXMLRegistry.GAME_END_DIALOG);
        Parent parent = null;
        Stage stage = new Stage();
        try {
            parent = loader.load();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        IController controller = loader.getController();
        controller.setModel(model);
        stage.setScene(new Scene(parent, 700, 300));
        stage.initOwner(GameApplication.getStage());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();

        previousDialog = stage;

        return new TemplateGameEndDialog(parent);
    }

}
