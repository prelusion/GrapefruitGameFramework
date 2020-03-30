package com.grapefruit.gamework.framework;

import javafx.scene.image.Image;

public abstract class Assets {

    public abstract String getDisplayName();
    public abstract Image getIcon();
    public abstract Image getPieceImageByColor(Colors color);

}
