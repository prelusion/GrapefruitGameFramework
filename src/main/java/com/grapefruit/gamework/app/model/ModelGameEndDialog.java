package com.grapefruit.gamework.app.model;

import com.grapefruit.gamework.app.util.Command;

/**
 * The type Model game end dialog.
 */
public class ModelGameEndDialog implements IModel {

    private String resultMessage;
    private Command onCloseGame;
    private boolean showMenuButton;

    /**
     * Instantiates a new Model game end dialog.
     *
     * @param gameResult     the game result
     * @param onCloseGame    the on close game
     * @param showMenuButton the show menu button
     */
    public ModelGameEndDialog(String gameResult, Command onCloseGame, boolean showMenuButton) {
        this.resultMessage = gameResult;
        this.onCloseGame = onCloseGame;
        this.showMenuButton = showMenuButton;
    }

    /**
     * Gets game result.
     *
     * @return the game result
     */
    public String getGameResult() {
        return resultMessage;
    }

    /**
     * Gets on close game command.
     *
     * @return the on close game command
     */
    public Command getOnCloseGameCommand() {
        return onCloseGame;
    }

    /**
     * Gets show menu button.
     *
     * @return the show menu button
     */
    public boolean getShowMenuButton() {
        return showMenuButton;
    }
}
