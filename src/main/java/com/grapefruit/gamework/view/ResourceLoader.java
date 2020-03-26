package com.grapefruit.gamework.view;

import com.grapefruit.gamework.controller.ControllerMainWindow;
import com.grapefruit.gamework.controller.IController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.util.Callback;
import java.io.File;
import java.io.IOException;

public class ResourceLoader {

    private static String FXML_LOCATION = "/fxml/";
    private static String IMAGE_LOCATION = "/images/";
    private static String CSS_LOCATION = FXML_LOCATION + "css/";

    public enum ImageType{
        GAME_ICON,
        BACKGROUND
    }


    public FXMLLoader getViewLoader(String fxmlName){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(FXML_LOCATION + fxmlName));
        return loader;
    }

    public static Image loadImage(String imageName, ImageType imageType){
        Image image;
        String prefix = "";

        switch (imageType){
            case GAME_ICON:
                prefix = "gameicons/";
                break;

            case BACKGROUND:
                prefix = "background/";
                break;
        }

        image = new Image(IMAGE_LOCATION + prefix + imageName);
        return image;
    }

}
