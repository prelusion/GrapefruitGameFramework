package com.grapefruit.gamework.app.controller;

import com.grapefruit.gamework.app.GameApplication;
import com.grapefruit.gamework.app.model.IModel;
import com.grapefruit.gamework.app.model.ModelGame;
import com.grapefruit.gamework.app.model.ModelGameEndDialog;
import com.grapefruit.gamework.app.resources.ImageRegistry;
import com.grapefruit.gamework.app.util.ImageHelper;
import com.grapefruit.gamework.app.view.templates.GameEndDialogWindow.GameEndDialogFactory;
import com.grapefruit.gamework.framework.*;
import com.grapefruit.gamework.framework.network.CommandCallback;
import com.grapefruit.gamework.framework.network.Commands;
import com.grapefruit.gamework.framework.network.Helpers;
import com.grapefruit.gamework.framework.network.ServerManager;
import com.grapefruit.gamework.games.reversi.JarnoWinningMinimax;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.MapChangeListener;
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

import java.lang.reflect.Array;
import java.net.URL;
import java.util.*;

public class ControllerGame implements IController {

    private boolean destroyed = false;

    private ModelGame model;
    private Game game;
    private ServerManager serverManager;

    private HBox[][] boardTiles;

    private Player onlineGameLocalPlayer;
    private Player onlineGameOnlinePlayer;
    private Player playerA;
    private Player playerB;

    private boolean isFirstTurn = true;

    JarnoWinningMinimax minimaxAlgorithm = new JarnoWinningMinimax(10);
    Thread minimaxThread;

    /** listeners */
    ChangeListener<Number> turnChangeListener;
    MapChangeListener<Player, Integer> scoreChangeListener;

    @FXML
    private Text turnNumber;

    @FXML
    private Text timeLeft;

    @FXML
    private ImageView gameIcon;

    @FXML
    private Text gameName;

    @FXML
    private Text currentPlayerName;

    @FXML
    private Text currentColor;

    @FXML
    private Text playerNameA;

    @FXML
    private Text playerNameB;

    @FXML
    private Text playerScoreA;

    @FXML
    private Text playerScoreB;

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

        game = this.model.getGame();
        serverManager = this.model.getServerManager();

        setupAssets();
        setupObservableListeners();

        if (this.model.isOnlineGame()) {
            game.setTurnTimeout(10);

            setupServerEventHandlers();

            for (Player player : game.getPlayers()) {
                if (player.isLocal()) onlineGameLocalPlayer = player;
                else onlineGameOnlinePlayer = player;
            }
        }

        Player[] players = game.getPlayers();
        if (players.length < 1) {
            System.err.println("Less than 1 player");
            return;
        }

        playerA = players[0];
        playerB = players[1];
        playerNameA.setText(playerA.getName());
        playerNameB.setText(playerB.getName());

        currentPlayerName.setText(game.getCurrentPlayer().getName());
        currentColor.setText(game.getCurrentPlayer().getColor().toString());

        drawBoard();
        update();

        if (!this.model.isOnlineGame()) {
            game.setTurnTimeout(10);

            game.startTurnTimer();

            if (game.getCurrentPlayer().isLocal() && game.getCurrentPlayer().isAI()) {
                playAI();
            }
        } else {
            if (game.getCurrentPlayer().isLocal() && game.getCurrentPlayer().isAI()) {
                game.startTurnTimer();
                playAI();
            }
        }
    }

    private void setupAssets() {
        gameName.setText(this.model.getAssets().getDisplayName());
        gameIcon.setImage(this.model.getAssets().getIcon());
    }

    private void setupObservableListeners() {
        scoreChangeListener = (MapChangeListener<Player, Integer>) change -> updateInfo();

        turnChangeListener = (observableValue, oldValue, newValue) -> Platform.runLater(() -> {
            if ((int) newValue <= 0) {
                game.resetTurnTimer();

                if (!model.isOnlineGame()) {
                    createEndDialog("Turn timed out, you lose!");
                }
            } else {
                updateInfo();
            }
        });

        game.getBoard().scores.addListener(scoreChangeListener);
        game.getTurnTimeProperty().addListener(turnChangeListener);
    }

    private void setupServerEventHandlers() {
        serverManager.setMoveCallback((boolean success, String[] args) -> {
            game.resetTurnTimer();

            String playerName = args[0];
            String move = args[1];

            int[] rowcol = Helpers.convertMoveString(move, this.model.getGame().getBoard().getBoardSize());
            int row = rowcol[0];
            int col = rowcol[1];

            Game game = this.model.getGame();
            Player player = game.getPlayerByName(playerName);

            Platform.runLater(() -> {
                game.playMove(row, col, player);
                update();
                game.startTurnTimer();
            });
        });

        serverManager.setTurnCallback((boolean success, String[] args) -> {
            game.setCurrentPlayer(onlineGameLocalPlayer);
            Platform.runLater(this::update);

            if (game.getCurrentPlayer().isLocal() && game.getCurrentPlayer().isAI()) {
                Platform.runLater(() -> {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ignored) {}
                    playAI();
                });
            }
        });

        serverManager.setTurnTimeoutWinCallback((boolean success, String[] args) -> {
            game.resetTurnTimer();
            Platform.runLater(() -> {
                createEndDialog("Opponent's turn timed out, you win!");
                update();
            });
        });

        serverManager.setTurnTimeoutLoseCallback((boolean success, String[] args) -> {
            game.resetTurnTimer();
            Platform.runLater(() -> {
                createEndDialog("Turn timed out, you lose!");
                update();
            });
        });

        serverManager.setIllegalmoveWinCallback((boolean success, String[] args) -> {
            game.resetTurnTimer();
            Platform.runLater(() -> {
                createEndDialog("Opponent illegal move, you win!");
                update();
            });
        });

    }

    public void update() {
        updateBoard();
        updateInfo();
        checkFinished();
    }

    private void drawBoard() {
        Board board = game.getBoard();

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
        Player player = game.getCurrentPlayer();

        drawPieces();
        resetPossibleMoves();

        if (model.isOnlineGame() && player.isLocal()) {
            markPossibleMoves(game.getAvailableMoves(player), player.isAI());
        } else if (player.isLocal() && !player.isAI()) {
            markPossibleMoves(game.getAvailableMoves(player), player.isAI());
        }
    }

    private void updateInfo() {
        currentColor.setText(game.getCurrentPlayer().getColor().toString());
        currentPlayerName.setText(game.getCurrentPlayer().getName());
        turnNumber.setText(Integer.toString(game.getTurnCount()));
        timeLeft.setText(String.valueOf(game.getTurnSecondsLeft()));

        playerScoreA.setText(Integer.toString(game.getBoard().countPieces(playerA)));
        playerScoreB.setText(Integer.toString(game.getBoard().countPieces(playerB)));
    }

    public void checkFinished() {
        if (destroyed) {
            return;
        }

        if (!game.hasFinished()) {
            return;
        }

        if (game.isTie()) {
            createEndDialog("Tie!");
            return;
        }

        if (game.hasWinner()) {
            if (game.getWinner().isLocal()) {
                createEndDialog(game.getWinner().getName() + " has won the game!");
            } else {
                createEndDialog("You lose!");
            }
        }
    }

    private void drawPieces() {
        for (int row = 0; row < game.getBoard().getBoardSize(); row++) {
            for (int col = 0; col < game.getBoard().getBoardSize(); col++) {

                Tile tile = game.getBoard().getTile(row, col);

                if (game.getBoard().getPlayer(tile.getRow(), tile.getCol()) != null) {
                    Image pieceImage = model.getAssets().getPieceImageByColor(game.getBoard().getPlayer(tile.getRow(), tile.getCol()).getColor());
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

    private void resetPossibleMoves() {
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
    }

    private void markPossibleMoves(List<Tile> tiles, boolean isAI) {
        for (Tile tile : tiles) {
            HBox pane = boardTiles[tile.getRow()][tile.getCol()];
            Circle marker = new Circle(32.5, Paint.valueOf("blue"));
            marker.setFill(Color.rgb(100, 100, 100, 0.5));
            pane.getChildren().add(marker);
            marker.setStroke(Color.GREEN);

            if (!isAI) {
                marker.setOnMouseClicked(event -> {
                    playMove(tile.getRow(), tile.getCol(), model.getGame().getCurrentPlayer());
                });
            }
        }
    }

    private void playMove(int row, int col, Player player) {
        this.model.getGame().resetTurnTimer();

        if (model.isOnlineGame()) {
            playOnlineMove(row, col, player);
        } else {
            playOfflineMove(row, col, player);
        }
    }

    private void playOnlineMove(int row, int col, Player player) {
        if (!model.getServerManager().isConnected()) {
            createEndDialog("Server connection error");
            return;
        }

        model.getServerManager().queueCommand(Commands.setMove(
                (success, args) -> {
                    game.setCurrentPlayer(onlineGameOnlinePlayer);
                    Platform.runLater(this::update);
                },
                row,
                col,
                model.getGame().getBoard().getBoardSize()
        ));
    }

    private void playOfflineMove(int row, int col, Player player) {
        game.playMove(row, col, player);
        update();
        nextPlayer();

        if (game.getCurrentPlayer().isAI()) {

            if (!this.model.isOnlineGame()) {
                update();
            }

            Platform.runLater(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {
                }
                playAI();
            });
        }
    }

    private void playAI() {
        System.out.println("Play AI");
        if (game.hasFinished()) {
            return;
        }

        minimaxThread = new Thread(() -> {
            minimaxAlgorithm.startTimeout(9000);
            Tile tile = minimaxAlgorithm.calculateBestMove(
                    game.getBoard(),
                    game.getCurrentPlayer(),
                    game.getOpponentPlayer(),
                    game.getTurnCount()
            );
            Platform.runLater(() -> onFinishAI(tile));
        });

        minimaxThread.start();
    }

    private void onFinishAI(Tile tile) {
        if (tile == null) {
            nextPlayer();
            return;
        }

        game.resetTurnTimer();

        playMove(tile.getRow(), tile.getCol(), game.getCurrentPlayer());
    }

    private void nextPlayer() {
        if (game.hasFinished()) {
            return;
        }

        do {
            game.nextPlayer();
            update();
            if (destroyed) break;
            if (game.hasFinished()) break;
        } while (game.getAvailableMoves(game.getCurrentPlayer()).size() < 1);

        game.startTurnTimer();
    }

    private void createEndDialog(String message) {
        ModelGameEndDialog endDialogModel = new ModelGameEndDialog(message, this::onClose);
        GameEndDialogFactory.build(endDialogModel);
    }

    private HBox createBoardTile(int size, Color color, Tile tile) {
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        hbox.setMinSize(size, size);
        hbox.setPrefSize(size, size);
        Image background = ImageHelper.getRandomChunkOfImage(ImageRegistry.GREEN_BACKGROUND, 100, 100);
        hbox.setBackground(new Background(new BackgroundImage(
                background,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT
        )));
        hbox.getStyleClass().add(0, "game-board-tile");

        hbox.setOnMouseEntered(event -> hbox.getStyleClass().set(0, "game-board-tile-hover"));
        hbox.setOnMouseExited(event -> hbox.getStyleClass().set(0, "game-board-tile"));

        Image border = ImageHelper.getRandomChunkOfImage(ImageRegistry.WOOD_BACKGROUND, 100, 100);
        hbox.setBorder(new Border(new BorderImage(
                border,
                new BorderWidths(5, 5, 5, 5, false, false, false, false),
                Insets.EMPTY, BorderWidths.DEFAULT,
                false,
                BorderRepeat.REPEAT,
                BorderRepeat.REPEAT
        )));

        return hbox;
    }

    @FXML
    private void quitGame() {
        onClose();

        if (model.isOnlineGame() && !game.hasFinished()) {
            model.getServerManager().queueCommand(Commands.forfeit(new CommandCallback() {
                @Override
                public void onResponse(boolean success, String[] args) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            GameApplication.openLauncher();
                        }
                    });
                }
            }));
        } else {
            GameApplication.openLauncher();
        }
    }

    /**
     * This method stops all side effects.
     */
    private void onClose() {
        System.out.println("on close!");
        System.out.println("removing side effects");

        if (turnChangeListener != null) {
            game.getTurnTimeProperty().removeListener(turnChangeListener);
        }

        if (scoreChangeListener != null) {
            game.getBoard().scores.removeListener(scoreChangeListener);
        }

        if (serverManager != null) {
            if (!model.isTournament()) {
                serverManager.removeStartGameCallback();
            }

            serverManager.removeMoveCallback();
            serverManager.removeTurnCallback();
            serverManager.removeTurnTimeoutWinCallback();
            serverManager.removeTurnTimeoutLoseCallback();
            serverManager.removeIllegalmoveWinCallback();
        }

        if (minimaxAlgorithm != null) {
            minimaxAlgorithm.destroy();
        }

        if (minimaxThread != null) {
            minimaxThread.interrupt();
        }

        game.destroy();

        destroyed = true;
    }
}
