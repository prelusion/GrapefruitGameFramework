package com.grapefruit.gamework.framework.network;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


/**
 * The type Server connection.
 */
public class ServerConnection {

    private String serverIp;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private ServerManager manager;

    /**
     * Instantiates a new Server connection.
     *
     * @param manager Servermanager the manager for the server connection.
     */
    public ServerConnection(ServerManager manager) {
        this.manager = manager;
    }

    /**
     * Tries to connect to the server with ip-address passed in the parameter.
     *
     * @param serverIp String the server ip
     * @throws IOException the io exception
     */
    public void connect(String serverIp) throws IOException {
        this.serverIp = serverIp;
        socket = new Socket(serverIp, 7789);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        listen();
        startSending();
    }

    /**
     * Starts a Thread that listens to the server for responses or other messages and activates a callback function.
     *
     */
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
                                answer = answer.trim();
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
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        listenerThread.start();
    }

    /**
     * Closes the connection with the server.
     *
     * @throws IOException the io exception
     */
    public void closeConnection() throws IOException {
        socket.close();
        in.close();
        out.close();
    }

    /**
     * Checks wether the client is connected to the server.
     *
     * @return boolean if the server is connected.
     */
    public boolean isConnected() {
        return socket.isConnected();
    }

    /**
     * Starts a Thread that checks the commandqueue for commands waiting to be send.
     *
     */
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


    /**
     * The type Response challenge.
     */
    public class ResponseChallenge{

        private String challenger;
        private int number;
        private String gameType;

        /**
         * Instantiates a new Response challenge.
         */
        public ResponseChallenge() {
        }

        /**
         * Gets challenger.
         *
         * @return the challenger
         */
        public String getChallenger() {
            return challenger;
        }

        /**
         * Sets challenger.
         *
         * @param challenger the challenger
         */
        public void setChallenger(String challenger) {
            this.challenger = challenger;
        }

        /**
         * Gets number.
         *
         * @return the number
         */
        public int getNumber() {
            return number;
        }

        /**
         * Sets number.
         *
         * @param number the number
         */
        public void setNumber(int number) {
            this.number = number;
        }

        /**
         * Gets gametype.
         *
         * @return the gametype
         */
        public String getGametype() {
            return gameType;
        }

        /**
         * Sets gametype.
         *
         * @param gametype the gametype
         */
        public void setGametype(String gametype) {
            this.gameType = gametype;
        }
    }
}
