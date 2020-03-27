package com.grapefruit.gamework.framework;

import com.grapefruit.gamework.app.GameApplication;

import java.util.Scanner;

/**
 * This class is used to send and retrieve player input from a commandline interface.
 */
public class CliPlayerIO implements PlayerIO {

    private Scanner in = new Scanner(System.in);

    public CliPlayerIO() {}

    @Override
    public int[] askMove() {
        while (true) {
            System.out.println("Please enter move");
            String s = in.nextLine();
            if (s != null && s.length() == 2) {
                return new int[] {
                        Character.getNumericValue(s.charAt(0)),
                        Character.getNumericValue(s.charAt(1))
                };
            }
            System.out.println("Invalid input, row and column must be given as xx. Example: 12 (row 1, col 2)");
        }
    }

    public static void main(String[] args) {
        CliPlayerIO cliIO = new CliPlayerIO();
        int[] move = cliIO.askMove();
        System.out.println("Set move row: " + move[0] + " col: " + move[1]);
    }
}
