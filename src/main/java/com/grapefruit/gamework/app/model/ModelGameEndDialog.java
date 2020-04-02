package com.grapefruit.gamework.app.model;

import com.grapefruit.gamework.framework.Game;

public class ModelGameEndDialog implements IModel {

    private String resultMessage;

    public ModelGameEndDialog(String gameResult){
        this.resultMessage = gameResult;
    }

    public String getGameResult() {
        return resultMessage;
    }
}
