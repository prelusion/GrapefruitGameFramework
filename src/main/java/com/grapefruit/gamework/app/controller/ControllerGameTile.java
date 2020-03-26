package com.grapefruit.gamework.app.controller;

import com.grapefruit.gamework.app.model.IModel;
import com.grapefruit.gamework.app.model.ModelGameTile;

import javafx.fxml.FXML;

import javafx.scene.effect.BlurType;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerGameTile implements IController{


    private ModelGameTile model;

    @FXML
    private HBox gameTile;

    @FXML
    private ImageView gameIcon;

    @FXML
    private Text gameName;

    @FXML
    private URL location;

    @FXML
    private ResourceBundle resources;

    public ControllerGameTile()
    {
    }

    private void initialize()
    {
        gameName.setText(model.getGame().getName());
        gameIcon.setImage(model.getGame().getIcon());
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
        initialize();
    }
}
