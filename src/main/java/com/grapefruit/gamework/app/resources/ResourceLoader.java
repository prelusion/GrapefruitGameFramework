package com.grapefruit.gamework.app.resources;

import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;

public class ResourceLoader {

    private static String FXML_LOCATION = "/fxml/";
    private static String IMAGE_LOCATION = "/images/";
    private static String CSS_LOCATION = FXML_LOCATION + "css/";

    public enum ImageType{
        GAME_ICON,
        ASSET
    }


    /**
     * @param fxmlName of relevant FXML to build loader from
     * @return FXMLLoader for specified FXML file
     */
    public FXMLLoader getViewLoader(String fxmlName){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(FXML_LOCATION + fxmlName));
        return loader;
    }

    /**
     *
     * @param imageName file name
     * @param imageType image type used to find directory
     * @return Image
     */
    public static Image loadImage(String imageName, ImageType imageType){
        Image image;
        String prefix = "";

        switch (imageType){
            case GAME_ICON:
                prefix = "gameicons/";
                break;

            case ASSET:
                prefix = "assets/";
                break;
        }

        image = new Image(IMAGE_LOCATION + prefix + imageName);
        return image;
    }

}
