package com.grapefruit.gamework.framework.network;

import org.junit.Test;

import java.io.IOException;

public class ConnectionTest {

    private MessageController controller;

    public ConnectionTest() {
        try {
            controller = new MessageController();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("error in constructor of test class");
        }
    }

    @Test
    public void testCommand(){
        send("login rick");
        send("subscribe Reversi");
        send("get gamelist");
        send("get playerlist");


        while (true){}
    }

    private void send(String command) {
        try {
            controller.enterCommand(command);
        } catch (IOException e){
            e.printStackTrace();
            System.out.println("error occured while trying to connect to the server");
        }
    }
}
