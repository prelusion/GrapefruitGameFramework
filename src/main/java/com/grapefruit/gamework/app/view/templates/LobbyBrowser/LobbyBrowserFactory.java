package com.grapefruit.gamework.app.view.templates.LobbyBrowser;

import com.grapefruit.gamework.app.controller.IController;
import com.grapefruit.gamework.app.model.IModel;
import com.grapefruit.gamework.app.resources.FXMLRegistry;
import com.grapefruit.gamework.app.resources.ResourceLoader;
import com.grapefruit.gamework.app.view.templates.Template;
import com.grapefruit.gamework.app.view.templates.TemplateFactory;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

/**
 * The type Lobby browser factory.
 */
public class LobbyBrowserFactory implements TemplateFactory {

    private static LobbyBrowserFactory factory = new LobbyBrowserFactory();

    /**
     * Build template.
     *
     * @param model the model
     * @return the template
     */
    public static Template build(IModel model){
        return factory.buildTemplate(model);
    }

    private LobbyBrowserFactory(){
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Template buildTemplate(IModel model) {
        FXMLLoader loader = new ResourceLoader().getViewLoader(FXMLRegistry.LOBBY_BROWSER);
        Parent parent = null;
        try {
            parent = loader.load();
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
        IController controller = loader.getController();
        controller.setModel(model);
        return new TemplateLobbyBrowser(parent);
    }
}
