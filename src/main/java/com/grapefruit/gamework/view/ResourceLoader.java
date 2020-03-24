package com.grapefruit.gamework.view;

import com.grapefruit.gamework.controller.ControllerMainWindow;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.util.Callback;

import java.io.IOException;

public class ResourceLoader {

    private static String FXML_LOCATION = "/fxml/";
    private static String IMAGE_LOCATION = "/images/";

    public enum ImageType{
        GAME_ICON,
        BACKGROUND
    }


    public Parent loadFXML(String fxmlName){
        Parent resource = null;
        FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory(new Callback<Class<?>, Object>() {
            @Override
            public Object call(Class<?> controllerClass) {
                if (controllerClass == ControllerMainWindow.class) {
                    ControllerMainWindow controller = new ControllerMainWindow();
                    return controller ;
                } else {
                    try {
                        return controllerClass.newInstance();
                    } catch (Exception exc) {
                        throw new RuntimeException(exc); // just bail
                    }
                }
            }
        });

        try {
            loader.setLocation(getClass().getResource(FXML_LOCATION + fxmlName));
            resource = loader.load();
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
