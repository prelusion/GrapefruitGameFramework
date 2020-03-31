package com.grapefruit.gamework.app.controller;

import com.grapefruit.gamework.app.model.IModel;
import com.grapefruit.gamework.app.model.ModelGame;
import com.grapefruit.gamework.app.model.ModelGameEndDialog;
import com.grapefruit.gamework.app.resources.ImageRegistry;
import com.grapefruit.gamework.app.util.ImageHelper;
import com.grapefruit.gamework.app.view.templates.GameEndDialogWindow.GameEndDialogFactory;
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
import javafx.scene.text.Text;

import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerGame implements IController{


    private ModelGame model;

    private GridPane boardPane;

    private HBox[][] boardTiles;

    @FXML
    private ImageView gameIcon;

    @FXML
    private Text gameName;

    @FXML
    private Text currentTurnPlayer;

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
        gameName.setText(this.model.getAssets().getDisplayName());
        gameIcon.setImage(this.model.getAssets().getIcon());

        updateBoard();
    }

    private void drawBoard(Board board, boolean checkered){
        boardTiles = new HBox[board.getGrid().length][board.getGrid()[0].length];
        GridPane gridPane = new GridPane();
        Tile[][] tiles = board.getGrid();
        int tileSize = 80;

        boolean toggle = false;

        for (Tile[] column: tiles){
            for (Tile tile: column){
                HBox hbox;
                if (checkered) {
                    if (toggle) {
                        hbox = createBoardTile(tileSize, Color.rgb(232, 214, 202), tile);
                        toggle = !toggle;
                    } else {
                        hbox = createBoardTile(tileSize, Color.rgb(59, 41, 29), tile);
                        toggle = !toggle;
                    }
                } else {
                    hbox = createBoardTile(tileSize, Color.GREEN, tile);
                }
                gridPane.add(hbox, tile.getCol(), tile.getRow(), 1, 1);
                boardTiles[tile.getRow()][tile.getCol()] = hbox;
            }
            toggle = !toggle;
        }

        gameBoard.getChildren().removeAll(gameBoard.getChildren());
        gameBoard.getChildren().add(gridPane);
        boardPane = gridPane;
    }

    private void updateBoard(){
        showPlayerPieces();
        markPossibleMoves(model.getGame().getAvailableMoves(model.getGame().getCurrentPlayer()));
        currentTurnPlayer.setText(model.getGame().getCurrentPlayer().getName());
        if (model.getGame().getGameResult() == Game.GameResult.TIE || model.getGame().getGameResult() == Game.GameResult.WINNER){
            if (model.getGame().getGameResult() == Game.GameResult.WINNER){
                if (true) {
                    createEndDialog("You win!");
                } else {
                    createEndDialog("You lose!");
                }
            }
            if (model.getGame().getGameResult() == Game.GameResult.TIE) {
                if (true) {
                    createEndDialog("Tie!");
                }
            }
        }
    }

    private void createEndDialog(String message){
        GameEndDialogFactory.build(new ModelGameEndDialog(message));
    }

    private HBox createBoardTile(int size, Color color, Tile tile){
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        hbox.setMinSize(size,size);
        hbox.setPrefSize(size,size);
        Image background = ImageHelper.getRandomChunkOfImage(ImageRegistry.GREEN_BACKGROUND, 100, 100);
        hbox.setBackground(new Background(new BackgroundImage(background, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
        hbox.getStyleClass().add(0, "game-board-tile");

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

    private void showPlayerPieces(){
        Tile[][] tiles = model.getGame().getBoard().getGrid();
        for (Tile[] column: tiles){
            for (Tile tile: column){
                if (model.getGame().getBoard().getPlayer(tile.getRow(), tile.getCol()) != null) {
                    Image pieceImage = model.getAssets().getPieceImageByColor(model.getGame().getBoard().getPlayer(tile.getRow(), tile.getCol()).getColor());
                    ImageView imageView = new ImageView();
                    imageView.setFitHeight(65);
                    imageView.setFitWidth(65);
                    imageView.setImage(pieceImage);

                    HBox hbox = boardTiles[tile.getRow()][tile.getCol()];
                    if (hbox.getChildren().size() > 0) {
                        hbox.getChildren().removeAll(hbox.getChildren());
                    }
                    hbox.getChildren().add(imageView);
                }
            }
        }
    }

    private void markPossibleMoves(List<Tile> tiles){
        for (HBox[] column: boardTiles){
            for (HBox hbox: column) {
                ObservableList<Node> nodes = hbox.getChildren();
                HashSet<Node> nodesToRemove = new HashSet<>();
                if (nodes.size() > 0) {
                    for (Node node : nodes) {
                        if (node instanceof Circle) {
                            nodesToRemove.add(node);
                        }
                    }
                    hbox.getChildren().removeAll(nodesToRemove);
                }
            }
        }


        for (Tile tile: tiles) {
            HBox pane = boardTiles[tile.getRow()][tile.getCol()];
            Circle marker = new Circle(32.5, Paint.valueOf("blue"));
            marker.setStroke(Color.BLACK);
            marker.setFill(Color.rgb(200, 200, 200, 0.5));
            pane.getChildren().add(marker);

            marker.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    model.getGame().doMove( tile.getCol(), tile.getRow());
                    updateBooard();
                }
            });
        }
    }
}
