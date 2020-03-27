package com.grapefruit.gamework.app.controller;

import com.grapefruit.gamework.app.model.IModel;

public interface IController {

    /**
     * Sets the relevant model for a controller;
     *
     * @Param IModel applicable model to be referenced from controller;
     */
    void setModel(IModel model);

}
