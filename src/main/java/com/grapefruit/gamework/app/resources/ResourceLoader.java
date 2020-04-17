package com.grapefruit.gamework.app.resources;

import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;

/**
 * The type Resource loader.
 */
public class ResourceLoader {

    private static String FXML_LOCATION = "/fxml/";
    private static String IMAGE_LOCATION = "/images/";
    private static String CSS_LOCATION = FXML_LOCATION + "css/";

    /**
     * The enum Image type.
     */
    public enum ImageType{
        /**
         * Game icon image type.
         */
        GAME_ICON,
        /**
         * Asset image type.
         */
        ASSET
    }


    /**
     * Get view loader fxml loader.
     *
     * @param fxmlName of relevant FXML to build loader from
     * @return FXMLLoader for specified FXML file
     */
    public FXMLLoader getViewLoader(String fxmlName){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(FXML_LOCATION + fxmlName));
        return loader;
    }

    /**
     * Load image image.
     *
     * @param imageName file name
     * @param imageType image type used to find directory
     * @return Image image
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

    /**
     * Load style sheet string.
     *
     * @param styleSheetName the style sheet name
     * @return the string
     */
    public String loadStyleSheet(String styleSheetName){
        return getClass().getResource(CSS_LOCATION + styleSheetName + ".css").toExternalForm();
    }

}
