package com.grapefruit.gamework.framework;

public class OfflineGameSession extends GameSession {
    /**
     * Constructor of OfflineGameSession
     * @Param moveSetter, The rules and conditions being made for the game.
     * @Param players, The players that has been made for a game.
     * @Param turnTimeout, The amount of time a player has for calculating or setting your turn.
     * @Param boardSize, The size of the board being made.
     */
    public OfflineGameSession(MoveSetter moveSetter, Player[] players, int turnTimeout, int boardSize) {
        super(moveSetter, players, turnTimeout, boardSize);
    }

    @Override
    /**
     * Start the GameSession and makeing sure that everything is being used.
     */
    public void start() {
        currentTurnPlayer = getPlayers()[0];
    }

    public Player getCurrentTurnPlayer(){
        return currentTurnPlayer;
    }

    /**
     * @param move, move is given to set the move on the board and apply all necessary changes.
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
