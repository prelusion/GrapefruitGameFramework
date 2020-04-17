package com.grapefruit.gamework.games.tictactoe;

import com.grapefruit.gamework.app.resources.ImageRegistry;
import com.grapefruit.gamework.framework.Assets;
import com.grapefruit.gamework.framework.Colors;
import javafx.scene.image.Image;

/**
 * The type Tic tac toe assets.
 */
public class TicTacToeAssets extends Assets {

    /**
     * The constant PIECE_BLACK.
     */
    public static Image PIECE_BLACK = ImageRegistry.TICTACTOE_GAME_PIECE_BLACK;
    /**
     * The constant PIECE_WHITE.
     */
    public static Image PIECE_WHITE = ImageRegistry.TICTACTOE_GAME_PIECE_WHITE;

    private static Image ICON = ImageRegistry.GAME_ICON_TICTACTOE;
    private static String DISPLAYNAME = "TicTacToe";
    private static String SERVER_ID = "Tic-tac-toe";

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDisplayName() {
        return DISPLAYNAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getServerId() {
        return SERVER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Image getIcon() {
        return ICON;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Image getPieceImageByColor(Colors color) {
        switch (color) {
            case BLACK:
                return PIECE_BLACK;

            case WHITE:
                return PIECE_WHITE;

            default:
                return null;
        }
    }
}
