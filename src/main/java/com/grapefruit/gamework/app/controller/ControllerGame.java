package com.grapefruit.gamework.app.controller;

import com.grapefruit.gamework.app.model.IModel;
import com.grapefruit.gamework.app.model.ModelGame;
import com.grapefruit.gamework.app.model.ModelGameEndDialog;
import com.grapefruit.gamework.app.resources.ImageRegistry;
import com.grapefruit.gamework.app.util.ImageHelper;
import com.grapefruit.gamework.app.view.templates.GameEndDialogWindow.GameEndDialogFactory;
import com.grapefruit.gamework.framework.*;
import com.grapefruit.gamework.framework.player.Player;
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
    private Text turnNumber;

    @FXML
    private Text timeLeft;

    @FXML
    private VBox scorePlayerName;

    @FXML
    private VBox scorePlayerScore;

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
        updateInfoPanel();
    }

    private void drawBoard(Board board, boolean checkered){
        boardTiles = new HBox[board.getBoardSize()][board.getBoardSize()];
        GridPane gridPane = new GridPane();
        int tileSize = 80;

        boolean toggle = false;

        for (int row = 0; row < board.getBoardSize(); row++) {
            for (int col = 0; col < board.getBoardSize(); col++) {
                HBox hbox;
                if (checkered) {
                    if (toggle) {
                        hbox = createBoardTile(tileSize, Color.rgb(232, 214, 202), board.getTile(row, col));
                        toggle = false;
                    } else {
                        hbox = createBoardTile(tileSize, Color.rgb(59, 41, 29), board.getTile(row, col));
                        toggle = true;
                    }
                } else {
                    hbox = createBoardTile(tileSize, Color.GREEN, board.getTile(row, col));
                }
                gridPane.add(hbox, row, col, 1, 1);
                boardTiles[col][row] = hbox;
            }
        }

        gameBoard.getChildren().removeAll(gameBoard.getChildren());
        gameBoard.getChildren().add(gridPane);
        boardPane = gridPane;
    }

    private void updateBoard(){
        boolean playerLocal = model.getLocalPlayers().contains(model.getGame().getCurrentPlayer());
        showPlayerPieces();
        markPossibleMoves(model.getGame().getAvailableMoves(model.getGame().getCurrentPlayer()), playerLocal);
        currentTurnPlayer.setText(model.getGame().getCurrentPlayer().getName());
        if (model.getGame().getGameResult() == Game.GameResult.TIE || model.getGame().getGameResult() == Game.GameResult.WINNER){
            if (model.getGame().getGameResult() == Game.GameResult.WINNER){
                if (model.getLocalPlayers().contains(model.getGame().getWinner())) {
                    if (model.getLocalPlayers().size() == 1) {
                        createEndDialog("You win!");
                    } else {
                        createEndDialog(model.getGame().getWinner().getName() + " has won the game!");
                    }
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
        for (int row = 0; row < model.getGame().getBoard().getBoardSize(); row++) {
            for (int col = 0; col < model.getGame().getBoard().getBoardSize(); col++) {

                Tile tile = model.getGame().getBoard().getTile(row, col);

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

    private void markPossibleMoves(List<Tile> tiles, boolean locallyAvailable){
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
            marker.setFill(Color.rgb(100, 100, 100, 0.5));
            pane.getChildren().add(marker);

            if (locallyAvailable) {
                marker.setStroke(Color.GREEN);
                marker.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        model.getGame().playMove(tile.getRow(), tile.getCol(), model.getGame().getCurrentPlayer());
                        model.getGame().nextPlayer();
                        updateBoard();
                        updateInfoPanel();
                    }
                });
            } else {
                marker.setStroke(Color.GREEN);
            }
        }
    }

    private void updateInfoPanel(){
        scorePlayerName.getChildren().removeAll(scorePlayerName.getChildren());
        scorePlayerScore.getChildren().removeAll(scorePlayerScore.getChildren());

        for (Player player: model.getGame().getPlayers()){
            scorePlayerName.getChildren().add(new Text("PlayerName"));
            scorePlayerScore.getChildren().add(new Text("100"));
        }

        currentTurnPlayer.setText(model.getGame().getCurrentPlayer().toString());
        timeLeft.setText(String.valueOf(model.getGame().getTurnTimeout()));
        //Todo implement turn number
        turnNumber.setText("99");
    }
}
