package com.grapefruit.gamework.framework;

public class OfflineGameSession extends GameSession {
    public OfflineGameSession(Player[] players, Rule[] rules, int turnTimeout, int boardSize) {
        super(players, rules, turnTimeout, boardSize);
    }

    @Override
    public void start() {
        while (true) {
            for (Player player : getPlayers()) {
                player.giveTurn(getBoard(), getTurnTimeout());
            }
        }
    }
}
