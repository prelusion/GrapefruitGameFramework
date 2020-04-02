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
                                Command command = manager.getFirstUnconfirmed();
                                command.confirm();
                                if (command.getResponseType() == ServerManager.ResponseType.CONFIRMONLY){
                                    manager.removeCommandFromQueue(command);
                                }
                            } else if (answer.startsWith("ERR")){
                                manager.getFirstUnconfirmed().confirm();
                            }
                            if (answer.contains("SVR") && answer.contains("[")){
                                List<String> arguments = Arrays.asList(answer.split("(?<=(['\"])\\b)(?:(?!\\1|\\\\).|\\\\.)*(?=\\1)"));
                                String[] args = new String[arguments.size()];
                                int i = 0;
                                for (String arg: arguments){
                                    args[i] = arg;
                                    i++;
                                }
                                manager.findFirstFittingCommand(ServerManager.ResponseType.LIST, true).doCallBack(args);
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

    public void closeConnection() throws IOException {
        socket.close();
        in.close();
        out.close();
    }

    public void startSending() {
        Thread timer = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.interrupted()) {
                    if (manager.getFirstUnconfirmed() != null) {
                        out.println(manager.getFirstUnconfirmed().getCommandString());
                    }
                    try{
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        timer.start();
    }
}
