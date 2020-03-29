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
    public ReversiPiece(Player player) {
        super(player);
    }

    public Image getImage(){
        if (getPlayer().getColor().equals("white")){
            return Reversi.IMAGE_WHITE_PIECE;
        } else if (getPlayer().getColor().equals("black")){
            return Reversi.IMAGE_BLACK_PIECE;
        }
        return null;
    }
}
