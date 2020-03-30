package com.grapefruit.gamework.reversi;

import com.grapefruit.gamework.framework.Piece;
import com.grapefruit.gamework.framework.Player;
import javafx.scene.image.Image;

public class ReversiPiece extends Piece {
    /**
     * Constructor of making a piece
     *
     * @param player
     */

    private Image pieceImage;

    public ReversiPiece(Player player, Image image) {
        super("idk", player);

        this.pieceImage = image;
    }

}
