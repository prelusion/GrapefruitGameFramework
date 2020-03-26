package com.grapefruit.gamework.view.templates.GameTile;

import com.grapefruit.gamework.controller.IController;
import com.grapefruit.gamework.model.IModel;
import com.grapefruit.gamework.view.FXMLRegistry;
import com.grapefruit.gamework.view.ResourceLoader;
import com.grapefruit.gamework.view.templates.MainWindow.MainWindowFactory;
import com.grapefruit.gamework.view.templates.MainWindow.TemplateMainWindow;
import com.grapefruit.gamework.view.templates.Template;
import com.grapefruit.gamework.view.templates.TemplateFactory;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class GameTileFactory implements TemplateFactory {

    private static GameTileFactory factory = new  GameTileFactory();

    public static Template build(IModel model){
        return factory.buildTemplate(model);
    }

    private GameTileFactory(){
    }

    @Override
    public Template buildTemplate(IModel model) {
        FXMLLoader loader = new ResourceLoader().getViewLoader(FXMLRegistry.GAME_TILE);
        Parent parent = null;
        try {
            parent = loader.load();
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
        IController controller = loader.getController();
        controller.setModel(model);
        return new TemplateGameTile(parent);
    }
}
