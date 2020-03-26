package com.grapefruit.gamework.app.view.templates;

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
