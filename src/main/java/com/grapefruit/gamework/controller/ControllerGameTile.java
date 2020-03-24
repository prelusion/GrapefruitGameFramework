package com.grapefruit.gamework.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerGameTile {


    @FXML
    private HBox gameTile;

    @FXML
    private URL location;

    @FXML
    private ResourceBundle resources;

    public ControllerGameTile()
    {
    }

    @FXML
    private void initialize()
    {
        System.out.println("test");
    }

    @FXML
    private void mouseMoved(){
        //gameTile.setStyle("linear-gradient(to right bottom, #FF6600 100%, #946B6B 64%)");
        System.out.println("test");
    }

}
