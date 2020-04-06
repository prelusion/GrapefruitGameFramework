package com.grapefruit.gamework.app.controller;

import com.grapefruit.gamework.app.model.IModel;
import com.grapefruit.gamework.app.model.ModelGame;
import com.grapefruit.gamework.app.model.ModelGameEndDialog;
import com.grapefruit.gamework.app.resources.ImageRegistry;
import com.grapefruit.gamework.app.util.ImageHelper;
import com.grapefruit.gamework.app.view.templates.GameEndDialogWindow.GameEndDialogFactory;
import com.grapefruit.gamework.framework.Board;
import com.grapefruit.gamework.framework.Player;
import com.grapefruit.gamework.framework.Tile;
import com.grapefruit.gamework.framework.network.CommandCallback;
import com.grapefruit.gamework.framework.network.Commands;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerGame implements IController {

    private ModelGame model;

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
    public ControllerGame() {
    }

    /**
     * Required for FXML
     * Sets the displayed icon and name for listed game.
     */
    private void initialize() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setModel(IModel model) {
        this.model = (ModelGame) model;

        gameName.setText(this.model.getAssets().getDisplayName());
        gameIcon.setImage(this.model.getAssets().getIcon());

        drawBoard(this.model.getGame().getBoard());

        updateBoard();
        updateInfo();
        checkFinished();
    }

    private void drawBoard(Board board) {
        boardTiles = new HBox[board.getBoardSize()][board.getBoardSize()];
        GridPane gridPane = new GridPane();
        int tileSize = 80;

        for (int row = 0; row < board.getBoardSize(); row++) {
            for (int col = 0; col < board.getBoardSize(); col++) {
                HBox hbox = createBoardTile(tileSize, Color.GREEN, board.getTile(row, col));
                gridPane.add(hbox, row, col, 1, 1);
                boardTiles[col][row] = hbox;
            }
        }

        gameBoard.getChildren().removeAll(gameBoard.getChildren());
        gameBoard.getChildren().add(gridPane);
    }

    private void updateBoard() {
        Player player = model.getGame().getCurrentPlayer();
        drawPieces();
        markPossibleMoves(model.getGame().getAvailableMoves(player), true);
    }

    private void updateInfo() {
        scorePlayerName.getChildren().removeAll(scorePlayerName.getChildren());
        scorePlayerScore.getChildren().removeAll(scorePlayerScore.getChildren());

        for (Player ignored : model.getGame().getPlayers()) {
            scorePlayerName.getChildren().add(new Text("PlayerName"));
            scorePlayerScore.getChildren().add(new Text("100"));
        }

        currentTurnPlayer.setText(model.getGame().getCurrentPlayer().getColor().toString());

        timeLeft.setText(String.valueOf(model.getGame().getTurnTimeout()));
        //Todo implement turn number
        turnNumber.setText("99");
    }

    public void checkFinished() {
        if (model.getGame().hasFinished()) {
            if (model.getGame().hasWinner()) {
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
            if (model.getGame().isTie()) {
                createEndDialog("Tie!");
            }
        }
    }

    private void drawPieces() {
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

    private void markPossibleMoves(List<Tile> tiles, boolean locallyAvailable) {
        for (HBox[] column : boardTiles) {
            for (HBox hbox : column) {
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

        for (Tile tile : tiles) {
            HBox pane = boardTiles[tile.getRow()][tile.getCol()];
            Circle marker = new Circle(32.5, Paint.valueOf("blue"));
            marker.setFill(Color.rgb(100, 100, 100, 0.5));
            pane.getChildren().add(marker);

            if (locallyAvailable) {
                marker.setStroke(Color.GREEN);
                marker.setOnMouseClicked(event -> {
                    playMove(tile.getRow(), tile.getCol(), model.getGame().getCurrentPlayer());
                });
            } else {
                marker.setStroke(Color.GREEN);
            }
        }
    }

    private void playMove(int row, int col, Player player) {
        if (!model.isOnlineGame()) {
            updateMove(row, col, player);
            return;
        }

        if (!model.getServerManager().isConnected()) {
            System.out.println("SERVER CONNECTION ERROR");
            return;
        }

        CommandCallback callback = (success, args) -> updateMove(row, col, player);

        model.getServerManager().queueCommand(
                Commands.setMove(callback, row, col, model.getGame().getBoard().getBoardSize())
        );
    }

    private void updateMove(int row, int col, Player player) {
        model.getGame().playMove(row, col, player);
        model.getGame().nextPlayer();
        updateBoard();
        updateInfo();
        checkFinished();
    }

    private void createEndDialog(String message) {
        GameEndDialogFactory.build(new ModelGameEndDialog(message));
    }

    private HBox createBoardTile(int size, Color color, Tile tile) {
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        hbox.setMinSize(size, size);
        hbox.setPrefSize(size, size);
        Image background = ImageHelper.getRandomChunkOfImage(ImageRegistry.GREEN_BACKGROUND, 100, 100);
        hbox.setBackground(new Background(new BackgroundImage(background, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
        hbox.getStyleClass().add(0, "game-board-tile");

        hbox.setOnMouseEntered(event -> hbox.getStyleClass().set(0, "game-board-tile-hover"));
        hbox.setOnMouseExited(event -> hbox.getStyleClass().set(0, "game-board-tile"));

        Image border = ImageHelper.getRandomChunkOfImage(ImageRegistry.WOOD_BACKGROUND, 100, 100);
        hbox.setBorder(new Border(new BorderImage(border, new BorderWidths(5, 5, 5, 5, false, false, false, false), Insets.EMPTY, BorderWidths.DEFAULT, false, BorderRepeat.REPEAT, BorderRepeat.REPEAT)));

        return hbox;
    }
}
