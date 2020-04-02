package com.grapefruit.gamework.framework.network;

import java.io.*;
import java.net.Socket;

public class ServerConnection {

    private String serverIp;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public ServerConnection(String serverIp) {
        this.serverIp = serverIp;
    }

    public void connect() throws IOException {
        socket = new Socket(serverIp, 7789);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        listen();
    }

    private void listen(){
        Thread listenerThread = new Thread(new Runnable() {
            public void run() {
                try {
                    while (socket.isConnected()) {
                        String answer = in.readLine();
                        if (!answer.equals("null")) {
//                            controller.answer(answer);
                       }
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        listenerThread.start();
    }

    public void sendCommand(String input) {
        System.out.println("command sent: " + input);
        out.println(input);
    }

    public void closeConnection() throws IOException {
        socket.close();
        in.close();
        out.close();
    }
}
