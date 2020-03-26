package com.grapefruit.gamework.view.templates;

import com.grapefruit.gamework.model.IModel;
import javafx.scene.Parent;

public abstract class Template {

    private Parent parent;

    public Template(Parent parent){
        this.parent = parent;
    }

    public Parent getParent() {
        return parent;
    }
}
