package com.grapefruit.gamework.controller;

import com.grapefruit.gamework.model.IModel;
import com.grapefruit.gamework.model.ModelGameTile;
import com.grapefruit.gamework.model.ModelMainWindow;
import javafx.fxml.FXML;
import javafx.scene.Parent;
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

public class ControllerGameTile implements IController{


    private ModelGameTile model;

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
        gameTile.setStyle("");
    }

    @FXML
    private void mouseExit(){
        gameTile.setEffect(null);
    }

    @FXML
    private void dragEntered(MouseEvent event){
        gameTile.setLayoutY(event.getY());
    }

    @FXML
    private void dragDropped(MouseEvent event){
        gameTile.setLayoutY(event.getY());
    }

    @FXML
    private void mouseClicked(MouseEvent event){
        gameTile.toBack();
        gameTile.getViewOrder();
        gameTile.getViewOrder();
    }

    @Override
    public void setModel(IModel model) {
        this.model = (ModelGameTile) model;
    }
}
