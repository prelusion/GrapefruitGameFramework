package com.grapefruit.gamework.app.view.templates.SettingsWindow;

import com.grapefruit.gamework.app.GameApplication;
import com.grapefruit.gamework.app.controller.IController;
import com.grapefruit.gamework.app.model.IModel;
import com.grapefruit.gamework.app.resources.FXMLRegistry;
import com.grapefruit.gamework.app.resources.ImageRegistry;
import com.grapefruit.gamework.app.resources.ResourceLoader;
import com.grapefruit.gamework.app.view.templates.Template;
import com.grapefruit.gamework.app.view.templates.TemplateFactory;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

/**
 * The type Settings window factory.
 */
public class SettingsWindowFactory implements TemplateFactory {

    private static SettingsWindowFactory factory = new SettingsWindowFactory();

    /**
     * Build template.
     *
     * @param model the model
     * @return the template
     */
    public static Template build(IModel model){
        return factory.buildTemplate(model);
    }

    private SettingsWindowFactory(){
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Template buildTemplate(IModel model) {
        FXMLLoader loader = new ResourceLoader().getViewLoader(FXMLRegistry.SETTINGS_WINDOW);
        Parent parent = null;
        Stage stage = new Stage();
        try {
            parent = loader.load();
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
        IController controller = loader.getController();
        controller.setModel(model);
        stage.setScene(new Scene(parent, 700, 300));
        stage.initOwner(GameApplication.getStage());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.getIcons().add(ImageRegistry.GAMEWORK_ICON);
        stage.show();
        return new TemplateSettingsWindow(parent);
    }

}
