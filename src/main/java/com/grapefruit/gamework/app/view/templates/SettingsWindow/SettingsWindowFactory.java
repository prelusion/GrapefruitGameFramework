package com.grapefruit.gamework.app.view.templates.SettingsWindow;

import com.grapefruit.gamework.app.controller.IController;
import com.grapefruit.gamework.app.model.IModel;
import com.grapefruit.gamework.app.resources.FXMLRegistry;
import com.grapefruit.gamework.app.resources.ResourceLoader;
import com.grapefruit.gamework.app.view.templates.Template;
import com.grapefruit.gamework.app.view.templates.TemplateFactory;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class SettingsWindowFactory implements TemplateFactory {

    private static SettingsWindowFactory factory = new SettingsWindowFactory();

    public static Template build(IModel model){
        return factory.buildTemplate(model);
    }

    private SettingsWindowFactory(){
    }

    @Override
    public Template buildTemplate(IModel model) {
        FXMLLoader loader = new ResourceLoader().getViewLoader(FXMLRegistry.SETTINGS_WINDOW);
        Parent parent = null;
        try {
            parent = loader.load();
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
        IController controller = loader.getController();
        controller.setModel(model);
        return new TemplateSettingsWindow(parent);
    }

}
