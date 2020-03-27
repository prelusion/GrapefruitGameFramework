package com.grapefruit.gamework.app.view.templates.TableSetting;

import com.grapefruit.gamework.app.controller.IController;
import com.grapefruit.gamework.app.model.IModel;
import com.grapefruit.gamework.app.resources.FXMLRegistry;
import com.grapefruit.gamework.app.resources.ResourceLoader;
import com.grapefruit.gamework.app.view.templates.Template;
import com.grapefruit.gamework.app.view.templates.TemplateFactory;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class TableSettingFactory implements TemplateFactory {

    private static TableSettingFactory factory = new TableSettingFactory();

    public static Template build(IModel model){
        return factory.buildTemplate(model);
    }

    private TableSettingFactory(){
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Template buildTemplate(IModel model) {
        FXMLLoader loader = new ResourceLoader().getViewLoader(FXMLRegistry.TABLE_SETTING);
        Parent parent = null;
        try {
            parent = loader.load();
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
        IController controller = loader.getController();
        controller.setModel(model);
        return new TemplateTableSetting(parent);
    }

}
