package com.grapefruit.gamework.framework.network;

import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ServerConnection {

    private String serverIp;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private ServerManager manager;
    private boolean connected;

    public ServerConnection(ServerManager manager) {
        this.manager = manager;
    }

    public void connect(String serverIp) throws IOException {
        this.serverIp = serverIp;
        socket = new Socket(serverIp, 7789);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        listen();
        startSending();
        if (socket.isConnected()){
            connected = true;
        }
    }

    private void listen(){
        Thread listenerThread = new Thread(new Runnable() {
            public void run() {
                try {
                    while (socket.isConnected()) {
                        String answer = in.readLine();
                        if (answer != null && !answer.equals("null") && manager.commandsInQueue()) {
                            if (answer.equals("OK")){
                                Command command = manager.getFirstUnconfirmed();
                                command.confirm();
                                if (command.getResponseType() == ServerManager.ResponseType.CONFIRMONLY){
                                    manager.removeCommandFromQueue(command);
                                    command.doCallBack(true,null);
                                }
                            } else if (answer.startsWith("ERR")){
                                Command command = manager.getFirstUnconfirmed();
                                command.confirm();
                                String[] errors = new String[1];
                                errors[0] = answer;
                                command.doCallBack(false, errors);
                                manager.removeCommandFromQueue(command);

                            } else if (answer.contains("SVR") && answer.contains("[")){
                                answer = answer.strip();
                                int startArg = answer.indexOf("[");
                                String[] args = answer.substring(startArg + 1, answer.length() - 1).split(", ");

                                String[] result = new String[args.length];
                                int i = 0;
                                for (String element : args) {
                                    result[i] = element.substring(1, element.length() - 1);
                                    i++;
                                }


                                Command command = manager.findFirstFittingCommand(ServerManager.ResponseType.LIST, true);
                                command.confirm();
                                command.doCallBack(true, result);
                                manager.removeCommandFromQueue(command);
                            }
                       }
                        if (answer != null){
                            if (answer.startsWith("SVR GAME CHALLENGE CANCELLED")) {

                            }
                            if (answer.startsWith("SVR GAME CHALLENGE")){
                                Gson gson = new Gson();
                                ResponseChallenge responseChallenge = gson.fromJson(answer.replace("SVR GAME CHALLENGE", ""), ResponseChallenge.class);
                                //todo do something
                            }
                        }
                    }
                    connected = false;
                } catch (Exception e){
                    connected = false;
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

    public boolean isConnected() {
        return connected;
    }

    public void startSending() {
        Thread timer = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.interrupted()) {
                    Command command = manager.getFirstUnsent();
                    if (command != null && !command.isSent()) {
                        command.send();
                        out.println(command.getCommandString());
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


    public class ResponseChallenge{

        private String challenger;
        private int number;
        private String gameType;

        public ResponseChallenge() {
        }

        public String getChallenger() {
            return challenger;
        }

        public void setChallenger(String challenger) {
            this.challenger = challenger;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public String getGametype() {
            return gameType;
        }

        public void setGametype(String gametype) {
            this.gameType = gametype;
        }
    }
}
