package com.grapefruit.gamework.app.model;

import com.grapefruit.gamework.app.util.Command;
import com.grapefruit.gamework.framework.Game;

import java.awt.event.ActionListener;

public class ModelGameEndDialog implements IModel {

    private String resultMessage;
    private Command onCloseGame;

    public ModelGameEndDialog(String gameResult, Command onCloseGame){
        this.resultMessage = gameResult;
        this.onCloseGame = onCloseGame;
    }

    public String getGameResult() {
        return resultMessage;
    }

    public Command getOnCloseGameCommand() {
        return onCloseGame;
    }
}
