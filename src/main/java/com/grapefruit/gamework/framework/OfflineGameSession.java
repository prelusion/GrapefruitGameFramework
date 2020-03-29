package com.grapefruit.gamework.framework;

public class OfflineGameSession extends GameSession {

    private Player currentTurnPlayer;

    public OfflineGameSession(MoveSetter moveSetter, Player[] players, int turnTimeout, int boardSize) {
        super(moveSetter, players, turnTimeout, boardSize);
    }

    @Override
    public void start() {
        currentTurnPlayer = getPlayers()[0];
    }

    public Player getCurrentTurnPlayer(){
        return currentTurnPlayer;
    }

    /**
     * @param Move, move is given to set the move on the board and apply all necessary changes.
     */
    public void setMove(Move move) {
        if (getCurrentAvailableMoves(currentTurnPlayer).contains(move.getTile())) {
            if (currentTurnPlayer == getPlayers()[0]) {
                currentTurnPlayer = getPlayers()[1];
            } else if (currentTurnPlayer == getPlayers()[1]) {
                currentTurnPlayer = getPlayers()[0];
            }
        }
    }
}
