package com.grapefruit.gamework.cli;

import com.grapefruit.gamework.framework.*;
import com.grapefruit.gamework.tictactoe.TicTacToeFactory;

import java.util.Scanner;

public class CLI {

    private static CLI cli;
    private Scanner in = new Scanner(System.in);
    private Player[] players;
    private Player currPlayer;
    private int currPlayerIdx = 0;
    private GameSession session;

    enum State {
        LOBBY,
        PLAYING,
    }

    private State currState = State.LOBBY;

    public static void main(String[] args) {
        getCLI().eventLoop();
    }

    private CLI() {}

    public static CLI getCLI() {
        if (cli == null) cli = new CLI();
        return cli;
    }

    public void eventLoop() {
        System.out.println("Gamework started, press 'q' to quit");
        while (true) {
            showMessage();
            System.out.println("> ");
            String line = in.nextLine();
            if (line.length() == 1 && line.charAt(0) == 'q') {
                break;
            }
            handleInput(line);
        }
    }

    public void showMessage() {
        switch (currState) {
            case LOBBY:
                System.out.println("Enter 'start' to play game");
                break;
            case PLAYING:
                System.out.println("Enter move for player " + currPlayer.getName() + ". Example: 12 (row 1, column 2)");
                break;
        }
    }

    public void handleInput(String line) {
        switch (currState) {
            case LOBBY:
                if (line.equals("start")) {
                    players = new Player[] {new HumanPlayer("A", "red"),  new HumanPlayer("B", "red")};
                    Game game = TicTacToeFactory.create();
                    session = game.createSession(players);
                    currState = State.PLAYING;
                    giveTurn();
                }
                break;
            case PLAYING:
                if (line.length() == 2) {
                    int row = Character.getNumericValue(line.charAt(0));
                    int col = Character.getNumericValue(line.charAt(0));

                    System.out.println("Player " + currPlayer.getName() + " entered: " + row + ", " + col);

                    Move move = session.createMove(row, col, currPlayer);

                    if (session.isValidMove(move)) {
                        session.setMove(move);
                        giveTurn();
                    } else {
                        System.out.println("Invalid move, please try again");
                    }
                }
                break;
        }
    }

    public void giveTurn() {
        if (currPlayer == null) {
            currPlayer = players[currPlayerIdx];
        } else if (currPlayerIdx == 0) {
            currPlayerIdx = 1;
        } else if (currPlayerIdx == 1) {
            currPlayerIdx = 0;
        }
        currPlayer = players[currPlayerIdx];
    }
}
