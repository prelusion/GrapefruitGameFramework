package com.grapefruit.gamework.app.controller;

import com.grapefruit.gamework.app.model.IModel;

/**
 * The interface Controller.
 */
public interface IController {

    /**
     * Sets the relevant model for a controller;
     *
     * @param model the model
     * @Param IModel applicable model to be referenced from controller;
     */
    void setModel(IModel model);

}
