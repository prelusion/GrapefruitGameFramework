package com.grapefruit.gamework.app.controller;

import com.grapefruit.gamework.app.GameApplication;
import com.grapefruit.gamework.app.model.IModel;
import com.grapefruit.gamework.app.model.ModelGame;
import com.grapefruit.gamework.app.model.ModelGameEndDialog;
import com.grapefruit.gamework.app.resources.AppSettings;
import com.grapefruit.gamework.app.resources.ImageRegistry;
import com.grapefruit.gamework.app.util.ImageHelper;
import com.grapefruit.gamework.app.view.templates.GameEndDialogWindow.GameEndDialogFactory;
import com.grapefruit.gamework.framework.*;
import com.grapefruit.gamework.network.Commands;
import com.grapefruit.gamework.network.Helpers;
import com.grapefruit.gamework.network.ServerManager;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
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

import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerGame implements IController {

    private boolean destroyed = false;

    private ModelGame model;
    private Game game;
    private ServerManager serverManager;
    private boolean settingMove = false;
    private HBox[][] boardTiles;

    private Player onlineGameLocalPlayer;
    private Player onlineGameOnlinePlayer;
    private Player playerA;
    private Player playerB;
    private boolean isFirstTurn = false;
    private boolean currentlySettingTurn = false;
    Thread minimaxThread;
    private boolean firstAI = true;
    private boolean alreadySettingAI = false;

    int offlineTurnTimeout = 60;

    /** Minimax Default Configuration */
    MinimaxAlgorithm minimaxAlgorithm;
    int onlineTurnTimeout = 5;
    int onlineTurnTimeoutAI = (onlineTurnTimeout * 1000) - 1400;
    int onlineTurnTimeoutAIFirstTurn = onlineTurnTimeoutAI / 2;

    /**
     * listeners
     */
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
    private Pane gameBoard;

    @FXML
    private URL location;

    @FXML
    private ResourceBundle resources;

    @FXML
    public Text playerTurnLabel;

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
        minimaxAlgorithm = game.getMinimaxAlgorithm();
        serverManager = this.model.getServerManager();

        setOnlineTimeout(AppSettings.getSettings().getTimeout());

        GameEndDialogFactory.destroyPreviousDialogIfExists();

        System.out.println("Started game session");

        if (this.model.isOnlineGame()) {
            setupServerEventHandlers();
        }

        if (this.model.isTournament()) {
            System.out.println("Playing game in tournament mode");
        }

        game.getBoard().printStrategicValues();

        setupAssets();
        setupObservableListeners();

        if (this.model.isOnlineGame()) {
            game.setTurnTimeout(onlineTurnTimeout);

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

        System.out.println("Player with first move: " + currentPlayerName);

        drawBoard();
        update();

        if (!this.model.isOnlineGame()) {
            game.setTurnTimeout(offlineTurnTimeout);

            game.startTurnTimer();

            if (game.getCurrentPlayer().isLocal() && game.getCurrentPlayer().isAI()) {
                System.out.println("calling play ai ln. 184");
                new Thread(() -> {
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException ignored) {};

                    playAI();
                }).start();
            }
        } else {
            game.startTurnTimer();

            if (serverManager.getMoveTooFast()) {
                System.out.println("Executing on move too fast");
                onMove(serverManager.getMoveTooFastArgs());
            }

            if (serverManager.getTurnTooFast()) {
                game.setCurrentPlayer(onlineGameLocalPlayer);
                System.out.println("Executing on turn too fast");

                isFirstTurn = true;

                if (firstAI && !currentlySettingTurn) {
                    currentlySettingTurn = true;
                    firstAI = false;
                    onTurn();
                }

            } else if (game.getCurrentPlayer().isLocal() && game.getCurrentPlayer().isAI()) {
                game.setCurrentPlayer(onlineGameLocalPlayer);

                isFirstTurn = true;

                if (firstAI && !currentlySettingTurn) {
                    currentlySettingTurn = true;
                    firstAI = false;
                    alreadySettingAI = true;
                    System.out.println("calling play ai ln. 208");
                    playAI();
                } else {
                    System.out.println("ignore play ai ln. 214");
                }
            } else {
                firstAI = false;
                System.out.println("Not starting AI");
            }
        }
    }

    public void setOnlineTimeout(int timeout) {
        onlineTurnTimeout = timeout;
        onlineTurnTimeoutAI = (onlineTurnTimeout * 1000) - 1400;
        if (onlineTurnTimeoutAI <= 1) onlineTurnTimeoutAI = 1;
        onlineTurnTimeoutAIFirstTurn = onlineTurnTimeoutAI / 2;
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
                    stopSideEffects();
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
        System.out.println("Initializing server event handlers");
        serverManager.setMoveCallback((boolean success, String[] args) -> {
            System.out.println("move callback in controller");
            for (String arg : args) System.out.println(arg);
            onMove(args);
        });

        serverManager.setTurnCallback((boolean success, String[] args) -> {
            if (firstAI) {
                firstAI = false;
                System.out.println("ignore on turn ln. 271");
            }

            if (!firstAI || !alreadySettingAI) {
                alreadySettingAI = true;

                System.out.println("turn callback in controller");
                if (!currentlySettingTurn) {
                    currentlySettingTurn = true;
                    onTurn();
                } else {
                    System.out.println("ignore on turn ln. 261");
                }
            }
        });

        serverManager.setTurnTimeoutWinCallback((boolean success, String[] args) -> {
            game.resetTurnTimer();
            Platform.runLater(() -> {
                stopSideEffects();

                if (model.isAutoChallenge()) {
                    openLauncherBackQuit();
                    return;
                }

                createEndDialog("Opponent's turn timed out, you win!");
                update();

                if (model.isTournament()) openLauncherBackQuit();
            });
        });

        serverManager.setTurnTimeoutLoseCallback((boolean success, String[] args) -> {
            game.resetTurnTimer();
            Platform.runLater(() -> {
                stopSideEffects();

                if (model.isAutoChallenge()) {
                    openLauncherBackQuit();
                    return;
                }

                createEndDialog("Turn timed out, you lose!");
                update();

                if (model.isTournament()) openLauncherBackQuit();
            });
        });

        serverManager.setIllegalmoveWinCallback((boolean success, String[] args) -> {
            game.resetTurnTimer();
            Platform.runLater(() -> {
                stopSideEffects();

                if (model.isAutoChallenge()) {
                    openLauncherBackQuit();
                    return;
                }

                createEndDialog("Opponent illegal move, you win!");
                update();

                if (model.isTournament()) openLauncherBackQuit();
            });
        });

        serverManager.setIllegalmoveLoseCallback((boolean success, String[] args) -> {
            game.resetTurnTimer();
            Platform.runLater(() -> {
                stopSideEffects();

                if (model.isAutoChallenge()) {
                    openLauncherBackQuit();
                    return;
                }

                createEndDialog("Illegal move, you lose!");
                update();

                if (model.isTournament()) openLauncherBackQuit();
            });
        });

        serverManager.setOnPlayerForfeitCallback((boolean success, String[] args) -> {
            game.resetTurnTimer();
            Platform.runLater(() -> {
                stopSideEffects();

                if (model.isAutoChallenge()) {
                    openLauncherBackQuit();
                    return;
                }

                createEndDialog("Opponent forfeited, you win!");
                update();

                if (model.isTournament()) openLauncherBackQuit();
            });
        });

        serverManager.setOnPlayerDisconnectCallback((boolean success, String[] args) -> {
            game.resetTurnTimer();
            Platform.runLater(() -> {
                stopSideEffects();

                if (model.isAutoChallenge()) {
                    openLauncherBackQuit();
                    return;
                }
                createEndDialog("Opponent disconnected, you win!");
                update();

                if (model.isTournament()) openLauncherBackQuit();
            });
        });

        System.out.println("Initialized server event handlers");
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
                HBox hbox = createBoardTile(tileSize);
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

        if (model.isOnlineGame()) {
            if (game.getCurrentPlayer().isLocal()) {
                playerTurnLabel.setText("Your turn");
            } else {
                playerTurnLabel.setText("Waiting for opponent");
            }
        } else {
            int nonAiCount = 0;
            for (Player player : game.getPlayers()) {
                if (!player.isAI()) {
                    nonAiCount++;
                }
            }

            if (nonAiCount == 2){
                playerTurnLabel.setText("Waiting for " + game.getCurrentPlayer().getName());
            } else if (!game.getCurrentPlayer().isAI()) {
                playerTurnLabel.setText("Your turn");
            } else {
                playerTurnLabel.setText("Waiting for opponent");
            }
        }

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

        stopSideEffects();

        if (model.isAutoChallenge()) {
            openLauncherBackQuit();
            return;
        }

        if (model.isTournament()) openLauncherBackQuit();


        if (game.isTie()) {
            createEndDialog("Tie!");

            if (model.isTournament()) openLauncherBackQuit();

            return;
        }

        if (game.hasWinner()) {
            if (game.getWinner().isLocal()) {
                createEndDialog(game.getWinner().getName() + " won the game!");
            } else {
                createEndDialog("You lose!");
            }
        }

        if (model.isTournament()) openLauncherBackQuit();
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


        if (!isDestroyed()) {
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
    }

    private void playOfflineMove(int row, int col, Player player) {
        game.playMove(row, col, player);
        update();
        nextPlayer();

        if (game.getCurrentPlayer().isAI()) {

            if (!this.model.isOnlineGame()) {
                update();
            }

            new Thread(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {
                }
                Platform.runLater(() -> {
                    System.out.println("calling play ai ln. 541");
                    playAI();
                });
            }).start();
        }
    }

    private void playAI() {
        System.out.println("Play AI");

        if (game.hasFinished() || isDestroyed()) {
            return;
        }

        minimaxThread = new Thread(() -> {
            System.out.println("is first turn: " + isFirstTurn);
            int timeout = isFirstTurn ? onlineTurnTimeoutAIFirstTurn : onlineTurnTimeoutAI;
            System.out.println("minimax timeout: " + timeout);

            minimaxAlgorithm.startTimeout(timeout);
            Tile tile = minimaxAlgorithm.calculateBestMove(
                    game.getBoard(),
                    game.getCurrentPlayer(),
                    game.getOpponentPlayer(),
                    game.getTurnCount()
            );
            System.out.println("Finish AI");

            isFirstTurn = false;

            if (isDestroyed()) {
                System.out.println("Game session destroyed, not sending AI generated move to server.");
                return;
            }

            Platform.runLater(() -> onFinishAI(tile));
        });

        minimaxThread.start();
    }

    private void onFinishAI(Tile tile) {
        currentlySettingTurn = false;

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
        ModelGameEndDialog endDialogModel = new ModelGameEndDialog(message, this::stopSideEffects, !model.isTournament());
        GameEndDialogFactory.build(endDialogModel);
    }

    private HBox createBoardTile(int size) {
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
        stopSideEffects();

        if (model.isOnlineGame() && !game.hasFinished() && !isDestroyed()) {
            model.getServerManager().queueCommand(Commands.forfeit(
                    (success, args) -> Platform.runLater(GameApplication::openLauncher)));
        } else {
            GameApplication.openLauncher();
        }
    }

    /**
     * This method stops all side effects.
     */
    private void stopSideEffects() {
        System.out.println("destroying game session");
        destroyed = true;

        if (turnChangeListener != null) {
            game.getTurnTimeProperty().removeListener(turnChangeListener);
        }

        if (scoreChangeListener != null) {
            game.getBoard().scores.removeListener(scoreChangeListener);
        }

        if (serverManager != null) {
            serverManager.setTurnTooFast(false);
            serverManager.setMoveTooFast(false);

            if (!model.isTournament() && !model.isAutoChallenge()) {
                System.out.println("removing start game callback");
                serverManager.removeStartGameCallback();

                serverManager.setTurnCallback((boolean success2, String[] args2) -> {
                    System.out.println("turn callback too fast in lobby browser");
                    model.getServerManager().setTurnTooFast(true);
                });

                serverManager.setMoveCallback((boolean success2, String[] args2) -> {
                    System.out.println("move callback too fast in lobby browser");
                    model.getServerManager().setMoveTooFast(true);
                    model.getServerManager().setMoveTooFastArgs(args2);
                });
            } else {
                serverManager.removeMoveCallback();
                serverManager.removeTurnCallback();
            }

            serverManager.removeTurnTimeoutWinCallback();
            serverManager.removeTurnTimeoutLoseCallback();
            serverManager.removeIllegalmoveWinCallback();
            serverManager.removeIllegalmoveLoseCallback();
            serverManager.removeOnPlayerForfeitCallback();
            serverManager.removeOnPlayerDisconnectCallbackCallback();
        }

        if (minimaxAlgorithm != null) {
            minimaxAlgorithm.destroy();
        }

        if (minimaxThread != null) {
            minimaxThread.interrupt();
        }

        game.destroy();
    }

    private boolean isDestroyed() {
        return destroyed;
    }

    public void openLauncherBackQuit() {
        GameApplication.openLauncherBack();
    }

    public void onTurn() {
        game.setCurrentPlayer(onlineGameLocalPlayer);

        Platform.runLater(() -> {
            update();

            while (settingMove) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException ignored) {}
            }

            if (game.getCurrentPlayer().isLocal() && game.getCurrentPlayer().isAI()) {
                System.out.println("calling play ai ln. 720");
                playAI();
            }

            currentlySettingTurn = false;
        });
    }

    public void onMove(String[] args) {
        settingMove = true;

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
            settingMove = false;
        });
    }
}
