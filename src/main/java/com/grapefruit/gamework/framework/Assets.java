package com.grapefruit.gamework.framework;

import javafx.scene.image.Image;

/**
 * The type Assets.
 */
public abstract class Assets {

    /**
     * Gets display name.
     *
     * @return the display name
     */
    public abstract String getDisplayName();

    /**
     * Gets server id.
     *
     * @return the server id
     */
    public abstract String getServerId();

    /**
     * Gets icon.
     *
     * @return the icon
     */
    public abstract Image getIcon();

    /**
     * Gets piece image by color.
     *
     * @param color the color
     * @return the piece image by color
     */
    public abstract Image getPieceImageByColor(Colors color);

}
