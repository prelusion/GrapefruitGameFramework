package com.grapefruit.gamework.app.controller;

import com.grapefruit.gamework.app.model.IModel;
import com.grapefruit.gamework.app.model.ModelGameTile;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * The type Controller game tile.
 */
public class ControllerGameTile implements IController {


    private ModelGameTile model;


    @FXML
    private Circle availableOnServer;

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

    /**
     * Required for FXML
     */
    public ControllerGameTile() {
    }

    /**
     * Required for FXML
     * Sets the displayed icon and name for listed game.
     */
    private void initialize() {
        gameName.setText(model.getGame().getAssets().getDisplayName());
        gameIcon.setImage(model.getGame().getAssets().getIcon());
    }

    /**
     * Called when mouse enters tile to updates hover effect.
     *
     * @Param event supplied by FXML
     */
    @FXML
    private void mouseEnter(MouseEvent event) {
        if (!model.isSelected()) {
            gameTile.getStyleClass().set(0, "menu-item-hover");
        } else {
            gameTile.getStyleClass().set(0, "menu-item-hover-selected");
        }
    }

    /**
     * Called when mouse enters tile to updates hover effect.
     *
     * @Param event supplied by FXML
     */
    @FXML
    private void mouseExit() {
        if (!model.isSelected()) {
            gameTile.getStyleClass().set(0, "menu-item-normal");
        } else {
            gameTile.getStyleClass().set(0, "menu-item-selected");

        }
    }

    /**
     * Called when mouse enters tile to updates selected effect.
     *
     * @Param event supplied by FXML
     */
    @FXML
    private void mouseClicked(MouseEvent event) {
        if (model.isSelected()) {
            model.gameUnselected();
        } else {
            model.gameSelected();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setModel(IModel model) {
        this.model = (ModelGameTile) model;
        if (this.model.isSelected()) {
            gameTile.getStyleClass().set(0, "menu-item-selected");
        } else {
            gameTile.getStyleClass().set(0, "menu-item-normal");
        }
        initialize();
        if (this.model.isAvailableOnServer()) {
            availableOnServer.setFill(Color.GREEN);
        } else {
            availableOnServer.setFill(Color.RED);
        }
    }
}
