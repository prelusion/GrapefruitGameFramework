package com.grapefruit.gamework.app.view.templates.SelectedGame;

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

/**
 * The type Selected game factory.
 */
public class SelectedGameFactory implements TemplateFactory {

    private static SelectedGameFactory factory = new SelectedGameFactory();

    /**
     * Build template.
     *
     * @param model the model
     * @return the template
     */
    public static Template build(IModel model){
        return factory.buildTemplate(model);
    }

    private SelectedGameFactory(){
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Template buildTemplate(IModel model) {
        FXMLLoader loader = new ResourceLoader().getViewLoader(FXMLRegistry.SELECTED_GAME);
        Parent parent = null;
        try {
            parent = loader.load();
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
        IController controller = loader.getController();
        controller.setModel(model);
        return new TemplateSelectedGame(parent);
    }

}
