package com.grapefruit.gamework.tictactoe;

import com.grapefruit.gamework.framework.*;

public class Main {
    public static void main(String[] args) {
        //Create players and put them in the array.
        Player playerA = new HumanPlayer("Delano", "blue", new CliPlayerIO());
        Player playerB = new HumanPlayer("Jarno", "red", new CliPlayerIO());
        Player[] players = new Player[] {playerA, playerB};

        //Create Rules for the game
        Rule tictactoeRules = new TicTacToeRules();

        //Create endconditions for the game
        GameCondition tictactoeEndConditions = new TicTacToeGameConditions();

        //Create MoveSetter for a game to make specific rules apply only for this game and also make unique moves for this game.
        MoveSetter tictactoeMoveSetter = new TicTacToeMoveSetter(tictactoeRules, tictactoeEndConditions);

        //Create a new game
        Game tictactoeGame = new TicTacToe();

        //Make a new session of the game with the Rules and Players
        GameSession session = tictactoeGame.createSession(tictactoeMoveSetter, players);

        //Start the session
        session.start();
    }
}
