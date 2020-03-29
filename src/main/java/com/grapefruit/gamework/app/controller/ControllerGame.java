package com.grapefruit.gamework.app.controller;

import com.grapefruit.gamework.app.model.IModel;
import com.grapefruit.gamework.app.model.ModelGame;
import com.grapefruit.gamework.app.model.ModelGameTile;
import com.grapefruit.gamework.framework.Board;
import com.grapefruit.gamework.framework.Tile;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerGame implements IController{


    private ModelGame model;

    @FXML
    private VBox gameBoard;

    @FXML
    private URL location;

    @FXML
    private ResourceBundle resources;

    /**
     * Required for FXML
     */
    public ControllerGame()
    {
    }

    /**
     * Required for FXML
     * Sets the displayed icon and name for listed game.
     */
    private void initialize()
    {
    }



    /**
     * {@inheritDoc}
     */
    @Override
    public void setModel(IModel model) {
        this.model = (ModelGame) model;
        drawBoard(this.model.getGame().getBoard(), false);
    }

    private void drawBoard(Board board, boolean checkered){
        GridPane gridPane = new GridPane();
        Tile[][] tiles = board.getGrid();
        int tileSize = 80;

        boolean toggle = false;

        for (Tile[] column: tiles){
            for (Tile tile: column){
                Pane pane;
                if (checkered) {
                    if (toggle) {
                        pane = createBoardTile(tileSize, Color.rgb(232, 214, 202));
                        toggle = !toggle;
                    } else {
                        pane = createBoardTile(tileSize, Color.rgb(59, 41, 29));
                        toggle = !toggle;
                    }
                } else {
                    pane = createBoardTile(tileSize, Color.GREEN);
                }
                gridPane.add(pane, tile.getX(), tile.getY(), 1, 1);
            }
            toggle = !toggle;
        }

        gameBoard.getChildren().removeAll(gameBoard.getChildren());
        gameBoard.getChildren().add(gridPane);
    }

    private Pane createBoardTile(int size, Color color){
        Pane pane = new Pane();
        pane.setMinSize(size,size);
        pane.setPrefSize(size,size);
        pane.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
        pane.getStyleClass().add(0, "game-board-tile");

        pane.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                pane.getStyleClass().set(0, "game-board-tile-hover");
            }
        });

        pane.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                pane.getStyleClass().set(0, "game-board-tile");
            }
        });
        return pane;
    }
}
