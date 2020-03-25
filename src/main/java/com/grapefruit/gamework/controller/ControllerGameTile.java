package com.grapefruit.gamework.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

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
    }

    @FXML
    private void mouseMoved(){
    }

    @FXML
    private void mouseEnter(MouseEvent event){
        gameTile.setEffect(new InnerShadow(BlurType.GAUSSIAN, Color.rgb(255,255,255), 64, 0,0,0 ));
    }

    @FXML
    private void mouseExit(){
        gameTile.setEffect(null);
    }

}
