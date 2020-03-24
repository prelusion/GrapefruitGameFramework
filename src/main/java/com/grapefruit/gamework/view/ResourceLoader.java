package com.grapefruit.gamework.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static javafx.application.ConditionalFeature.FXML;

public class ResourceLoader {

    private static FXMLLoader LOADER = new FXMLLoader();
    private static String FXML_LOCATION = "/fxml/";
    private static String IMAGE_LOCATION = "/images/";

    public enum ImageType{
        GAME_ICON,
        BACKGROUND
    }


    public Parent loadFXML(String fxmlName){
        Parent resource = null;
        try {
            LOADER.setLocation(getClass().getResource(FXML_LOCATION + fxmlName));
            resource = LOADER.load();
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
        return resource;
    }

    public Image loadImage(String imageName, ImageType imageType){
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
