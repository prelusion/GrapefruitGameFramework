package com.grapefruit.gamework.framework.network;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class MessageController {

    private ServerConnection connection;
    private Queue<String> commandQueue;
    private boolean blocked;

    public MessageController() throws IOException {
        connection = new ServerConnection(this, "127.0.0.1");
        connection.connect();
        commandQueue = new LinkedList<>();
        blocked = false;
        handleCommands();
    }

    public void answer(String answer) throws IllegalCommandException {
        if (!answer.equals("null") && !answer.equals("Strategic Game Server Fixed [Version 1.1.0]") && !answer.equals("(C) Copyright 2015 Hanzehogeschool Groningen")) {

        }
    }

    private void handleCommands() {
        Thread comHandler = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (!blocked && !commandQueue.isEmpty()) {
                        connection.sendCommand(commandQueue.poll());
                    }
                }
            }
        });
        comHandler.start();
    }

    public void enterCommand(String command) throws IOException {
        if (command.equals("disconnect")){
            connection.closeConnection();
        } else {
            commandQueue.add(command);
        }
    }
}
