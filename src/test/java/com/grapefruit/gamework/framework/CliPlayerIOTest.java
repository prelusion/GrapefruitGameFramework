package com.grapefruit.gamework.framework;

import org.junit.Test;

public class CliPlayerIOTest {
    @Test
    public void askMoveWithUserInput() {
        CliPlayerIO cliIO = new CliPlayerIO();
        int[] move = cliIO.askMove();
        System.out.println("Set move row: " + move[0] + " col: " + move[1]);
    }
}
