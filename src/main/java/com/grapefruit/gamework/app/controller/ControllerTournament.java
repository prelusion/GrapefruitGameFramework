package com.grapefruit.gamework.app.controller;

import com.grapefruit.gamework.app.model.IModel;
import com.grapefruit.gamework.app.model.ModelTournament;

public class ControllerTournament implements IController {

    ModelTournament modelTournament;

    @Override
    public void setModel(IModel model) {
        modelTournament = (ModelTournament) model;
    }

}
