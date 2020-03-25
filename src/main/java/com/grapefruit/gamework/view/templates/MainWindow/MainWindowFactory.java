package com.grapefruit.gamework.view.templates.MainWindow;

import com.grapefruit.gamework.controller.IController;
import com.grapefruit.gamework.model.IModel;
import com.grapefruit.gamework.view.FXMLRegistry;
import com.grapefruit.gamework.view.ResourceLoader;

import com.grapefruit.gamework.view.templates.Template;
import com.grapefruit.gamework.view.templates.TemplateFactory;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class MainWindowFactory implements TemplateFactory {

    private static MainWindowFactory factory = new  MainWindowFactory();

    public static Template build(IModel model){
        return factory.buildTemplate(model);
    }

    private MainWindowFactory(){
    }

    @Override
    public Template buildTemplate(IModel model) {
        FXMLLoader loader = new ResourceLoader().getViewLoader(FXMLRegistry.MAIN_WINDOW);
        Parent parent = null;
        try {
            parent = loader.load();
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
        IController controller = loader.getController();
        controller.setModel(model);
        return new TemplateMainWindow(parent);
    }

}
