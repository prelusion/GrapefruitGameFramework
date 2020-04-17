package com.grapefruit.gamework.app.model;


import com.grapefruit.gamework.framework.GameWrapper;

/**
 * The type Model game tile.
 */
public class ModelGameTile implements IModel {

    private GameWrapper game;
    private ModelMainWindow modelMainWindow;
    private boolean selected;
    private boolean availableOnServer;

    /**
     * Instantiates a new Model game tile.
     *
     * @param game       the game
     * @param mainWindow the main window
     */
    public ModelGameTile(GameWrapper game, ModelMainWindow mainWindow) {
        this.game = game;
        this.modelMainWindow = mainWindow;
        if (modelMainWindow.getSelectedGame() != null) {
            if (modelMainWindow.getSelectedGame() == game) {
                selected = true;
            }
        }
        if (mainWindow.getAvailableGames() != null && mainWindow.getAvailableGames().length > 0) {
            for (String availableGame : mainWindow.getAvailableGames()) {
                if (game.getAssets().getServerId().equals(availableGame)) {
                    availableOnServer = true;
                }
            }
        } else {
            availableOnServer = false;
        }
    }

    /**
     * Is available on server boolean.
     *
     * @return the boolean
     */
    public boolean isAvailableOnServer() {
        return availableOnServer;
    }

    /**
     * Gets game.
     *
     * @return returns Game
     */
    public GameWrapper getGame() {
        return game;
    }

    /**
     * Sets selected game in main window to model's game.
     */
    public void gameSelected() {
        modelMainWindow.setSelectedGame(game);
    }

    /**
     * Sets select game in main window to null
     */
    public void gameUnselected() {
        modelMainWindow.setSelectedGame(null);
    }

    /**
     * Is selected boolean.
     *
     * @return Whether game is selected;
     */
    public boolean isSelected() {
        return selected;
    }
}
