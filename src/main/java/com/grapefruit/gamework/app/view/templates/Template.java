package com.grapefruit.gamework.app.view.templates;

import javafx.scene.Parent;

/**
 * The type Template.
 */
public abstract class Template {

    private Parent parent;

    /**
     * Instantiates a new Template.
     *
     * @param parent to store in Template
     */
    public Template(Parent parent){
        this.parent = parent;
    }

    /**
     * Gets parent.
     *
     * @return Template 's parent
     */
    public Parent getParent() {
        return parent;
    }
}
