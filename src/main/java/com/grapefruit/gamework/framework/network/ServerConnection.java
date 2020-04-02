package com.grapefruit.gamework.framework.network;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;

public class ServerConnection {

    private String serverIp;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private ServerManager manager;

    public ServerConnection(String serverIp, ServerManager manager) {
        this.serverIp = serverIp;
        this.manager = manager;
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
                            if (answer.equals("OK")){
                                manager.getFirstUnconfirmed().confirm();
                            } else if (answer.startsWith("ERR")){
                                manager.getFirstUnconfirmed().confirm();
                            }
                            if (answer.contains("SVR") && answer.contains("[")){
                                List<String> items = Arrays.asList(answer.split("\\s*,\\s*"));
                            }
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
