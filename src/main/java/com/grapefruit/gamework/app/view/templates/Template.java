package com.grapefruit.gamework.app.view.templates;

import javafx.scene.Parent;

public abstract class Template {

    private Parent parent;

    /**
     * @param parent to store in Template
     */
    public Template(Parent parent){
        this.parent = parent;
    }

    /**
     *
     * @return Template's parent
     */
    public Parent getParent() {
        return parent;
    }
}
