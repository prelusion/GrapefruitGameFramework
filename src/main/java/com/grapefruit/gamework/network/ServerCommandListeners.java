package com.grapefruit.gamework.network;

import java.util.EventListener;

public class ServerCommandListeners {
    public interface StartGameListener extends EventListener {
        void onEvent(String playerToMove, String game, String opponent);
    }

    public interface MoveListener extends EventListener {
        void onEvent(int row, int col);
    }
}
