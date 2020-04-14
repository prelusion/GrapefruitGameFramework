package com.grapefruit.gamework.app.model;

import com.grapefruit.gamework.app.util.Command;
import com.grapefruit.gamework.framework.Game;

import java.awt.event.ActionListener;

public class ModelGameEndDialog implements IModel {

    private String resultMessage;
    private Command onCloseGame;
    private boolean showMenuButton;

    public ModelGameEndDialog(String gameResult, Command onCloseGame, boolean showMenuButton){
        this.resultMessage = gameResult;
        this.onCloseGame = onCloseGame;
        this.showMenuButton = showMenuButton;
    }

    public String getGameResult() {
        return resultMessage;
    }

    public Command getOnCloseGameCommand() {
        return onCloseGame;
    }

    public boolean getShowMenuButton() { return showMenuButton; }
}
