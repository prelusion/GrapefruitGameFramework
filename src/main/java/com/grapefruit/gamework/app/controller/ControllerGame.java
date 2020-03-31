package com.grapefruit.gamework.app.controller;

import com.grapefruit.gamework.app.model.IModel;
import com.grapefruit.gamework.app.model.ModelGame;
import com.grapefruit.gamework.app.resources.ImageRegistry;
import com.grapefruit.gamework.app.util.ImageHelper;
import com.grapefruit.gamework.framework.*;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;

public class ControllerGame implements IController{


    private ModelGame model;


    private GridPane boardPane;

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
        onTurnCompleted();
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
                        pane = createBoardTile(tileSize, Color.rgb(232, 214, 202), tile);
                        toggle = !toggle;
                    } else {
                        pane = createBoardTile(tileSize, Color.rgb(59, 41, 29), tile);
                        toggle = !toggle;
                    }
                } else {
                    pane = createBoardTile(tileSize, Color.GREEN, tile);
                }
                gridPane.add(pane, tile.getCol(), tile.getRow(), 1, 1);
            }
            toggle = !toggle;
        }

        gameBoard.getChildren().removeAll(gameBoard.getChildren());
        gameBoard.getChildren().add(gridPane);
        boardPane = gridPane;
    }

    private void onTurnCompleted(){
        markPossibleMoves(model.getGame().getAvailableMoves(model.getGame().getPlayers()[1]));
    }

    private Pane createBoardTile(int size, Color color, Tile tile){
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        hbox.setMinSize(size,size);
        hbox.setPrefSize(size,size);
        Image background = ImageHelper.getRandomChunkOfImage(ImageRegistry.GREEN_BACKGROUND, 100, 100);
        hbox.setBackground(new Background(new BackgroundImage(background, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
        hbox.getStyleClass().add(0, "game-board-tile");

        if (model.getGame().getBoard().getPlayer(tile.getRow(), tile.getCol()) != null){
            Image pieceImage = model.getAssets().getPieceImageByColor(model.getGame().getBoard().getPlayer(tile.getRow(), tile.getCol()).getColor());
            ImageView imageView = new ImageView();
            imageView.setFitHeight(65);
            imageView.setFitWidth(65);
            imageView.setImage(pieceImage);

            if (hbox.getChildren().size() > 0){
                hbox.getChildren().removeAll(hbox.getChildren());
            }
            hbox.getChildren().add(imageView);
        }

        hbox.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                hbox.getStyleClass().set(0, "game-board-tile-hover");
            }
        });

        hbox.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                hbox.getStyleClass().set(0, "game-board-tile");
            }
        });

        Image border = ImageHelper.getRandomChunkOfImage(ImageRegistry.WOOD_BACKGROUND, 100, 100);
        hbox.setBorder(new Border(new BorderImage(border, new BorderWidths(5, 5, 5, 5, false, false, false, false), Insets.EMPTY, BorderWidths.DEFAULT, false, BorderRepeat.REPEAT, BorderRepeat.REPEAT )));

        return hbox;
    }

    private void markPossibleMoves(HashSet<Tile> tiles){
        for (Node node: boardPane.getChildren()){
            HBox box = (HBox) node;
            ObservableList<Node> nodes =  box.getChildren();
            if (nodes.size() > 0) {
                for (Node node2 : nodes) {
                    if (node instanceof Circle) {
                        boardPane.getChildren().remove(node2);
                    }
                }
            }
        }


        for (Tile tile: tiles) {
            HBox pane = (HBox) boardPane.getChildren().get(tile.getRow()*model.getGame().getBoard().getGrid().length + tile.getCol());
            Circle marker = new Circle(32.5, Paint.valueOf("blue"));
            marker.setStroke(Color.BLACK);
            marker.setFill(Color.rgb(200, 200, 200, 0.5));
            pane.getChildren().add(marker);

            marker.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    //model.getGame().setMove(tile.getRow(), tile.getRow(), model.getGame().getPlayers()[0]);
                    model.getGame().getBoard().setPiece(tile.getRow(), tile.getCol(), model.getGame().getPlayers()[0]);
                    drawBoard(model.getGame().getBoard(), false );
                    onTurnCompleted();
                }
            });
        }
    }
}
