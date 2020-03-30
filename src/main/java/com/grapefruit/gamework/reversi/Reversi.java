package com.grapefruit.gamework.reversi;

import com.grapefruit.gamework.app.resources.ImageRegistry;
import com.grapefruit.gamework.framework.*;
import javafx.scene.image.Image;

public class Reversi extends Game {

    public static Image IMAGE_BLACK_PIECE = ImageRegistry.GAME_PIECE_BLACK;
    public static Image IMAGE_WHITE_PIECE = ImageRegistry.GAME_PIECE_WHITE;

    public Reversi(MoveSetter moveSetter) {
        super(moveSetter);
    }

    public GameSession createSession(GameSession session, Player[] players) {
        Tile[][] grid = session.getBoard().getGrid();
        grid[3][3].setPiece(new Piece("", players[0]));
        grid[4][4].setPiece(new Piece("", players[0]));
        grid[3][4].setPiece(new Piece("", players[1]));
        grid[4][3].setPiece(new Piece("", players[1]));

        return super.createSession(players);
    }
}
