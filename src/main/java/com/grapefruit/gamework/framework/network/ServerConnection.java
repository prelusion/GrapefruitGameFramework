package com.grapefruit.gamework.framework.network;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;


/**
 * The type Server connection.
 */
public class ServerConnection {

    public enum ChallengeStatus {
        CHALLENGE_SENT,
        CHALLENGE_RECEIVED
    }

    private String serverIp;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private ServerManager manager;
    private Thread timer;
    private Thread listenerThread;
    private HashMap<String, CommandCallback> serverCommandListeners = new HashMap<>();

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
        socket = new Socket(serverIp, 7789);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        listen();
        startSending();
    }

    /**
     * Starts a Thread that listens to the server for responses or other messages and activates a callback function.
     */
    private void listen() {
        listenerThread = new Thread(() -> {
            try {
                while (!listenerThread.isInterrupted()) {
                    String msg = in.readLine();

                    if (msg == null || msg.equals("null")) {
                        continue;
                    }

                    handleMessage(msg);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        listenerThread.start();
    }

    private void handleMessage(String msg) {
        if (manager.commandsInQueue()) {
            handleCommandResponses(msg);
        }

        if (msg.startsWith("SVR GAME CHALLENGE CANCELLED")) {
            int number = Integer.parseInt(msg.replace("SVR GAME CHALLENGE CANCELLED {CHALLENGENUMBER: \"", "").replace("\"}", ""));
            manager.getChallenges().removeIf(challenge -> challenge.getNumber() == number);

        } else if (msg.startsWith("SVR GAME CHALLENGE")) {
            ResponseChallenge challenge = parseChallenge(msg);
            challenge.setStatus(ChallengeStatus.CHALLENGE_RECEIVED);
            manager.addChallenge(challenge);

        } else if (msg.startsWith("SVR GAME MATCH")) {
            // SVR GAME MATCH {PLAYERTOMOVE: "jarno", GAMETYPE: "Reversi", OPPONENT: "bob"}
            String firstTurnName = parseCommandArg(msg, "PLATERTOMOVE");
            String opponentName = parseCommandArg(msg, "OPPONENT");
            CommandCallback listener = serverCommandListeners.get("onStartGame");
            if (listener != null) {
                listener.onResponse(true, new String[]{firstTurnName, opponentName});
            }

        } else if (msg.startsWith("SVR GAME YOURTURN")) {
            // SVR GAME YOURTURN {TURNMESSAGE: ""}
            String message = parseCommandArg(msg, "TURNMESSAGE");
            CommandCallback listener = serverCommandListeners.get("onTurn");
            if (listener != null) {
                listener.onResponse(true, new String[]{message});
            }

        } else if (msg.startsWith("SVR GAME MOVE")) {
            // SVR GAME MOVE {PLAYER: "alice", MOVE: "26", DETAILS: ""}
            String playerName = parseCommandArg(msg, "PLAYER");
            String move = parseCommandArg(msg, "MOVE");
            String details = parseCommandArg(msg, "DETAILS");

            CommandCallback listener = serverCommandListeners.get("onMove");
            if (listener != null) {
                listener.onResponse(
                        true,
                        new String[]{playerName, move, details}
                );
            }
        }
    }

    private void handleCommandResponses(String msg) {
        if (msg.equals("OK")) {
            Command command = manager.getFirstUnconfirmed();
            if (command != null && command.isSent()) {
                command.confirm();
                if (command.getResponseType() == ServerManager.ResponseType.CONFIRMONLY) {
                    manager.removeCommandFromQueue(command);
                    command.doCallBack(true, null);
                }
            }

        } else if (msg.startsWith("ERR")) {
            Command command = manager.getFirstUnconfirmed();
            if (command != null && command.isSent()) {
                command.confirm();
                String[] errors = new String[1];
                errors[0] = msg;
                command.doCallBack(false, errors);
                manager.removeCommandFromQueue(command);
            }

        } else if (msg.contains("SVR") && msg.contains("[")) {
            int startArg = msg.indexOf("[");
            String[] args = msg.substring(startArg + 1, msg.trim().length() - 1).split(", ");

            String[] result = new String[args.length];
            int i = 0;
            for (String element : args) {
                result[i] = element.substring(1, element.length() - 1);
                i++;
            }
            Command command = manager.findFirstFittingCommand(ServerManager.ResponseType.LIST, true);
            if (command != null && command.isSent()) {
                command.confirm();
                command.doCallBack(true, result);
                manager.removeCommandFromQueue(command);
            }
        }

    }

    private String parseCommandArg(String msg, String fieldname) {
        return msg.split(fieldname + ": \"")[1].split("\"")[0];
    }

    private ResponseChallenge parseChallenge(String challenge) {
        Gson gson = new Gson();
        String modifiedAnswer = challenge.replace("SVR GAME CHALLENGE", "");
        modifiedAnswer = modifiedAnswer.replace("CHALLENGER", "challenger");
        modifiedAnswer = modifiedAnswer.replace("CHALLENGENUMBER", "number");
        modifiedAnswer = modifiedAnswer.replace("GAMETYPE", "gameType");
        return gson.fromJson(modifiedAnswer, ResponseChallenge.class);
    }

    /**
     * Closes the connection with the server.
     *
     * @throws IOException the io exception
     */
    public void closeConnection() throws IOException {
        listenerThread.interrupt();
        timer.interrupt();
        boolean stillconnected = true;
        while (stillconnected) {
            if (!listenerThread.isAlive() && !timer.isAlive()) {
                socket.close();
                in.close();
                out.close();
                stillconnected = false;
            }
        }
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
     */
    public void startSending() {
        timer = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.interrupted()) {
                    Command command = manager.getFirstUnsent();
                    if (command != null && !command.isSent()) {
                        command.send();
                        out.println(command.getCommandString());
                    }
                    try {
                        Thread.sleep(300);
                    } catch (Exception e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        });
        timer.start();
    }

    public void setStartGameCallback(CommandCallback callback) {
        serverCommandListeners.put("onStartGame", callback);
    }

    public void setMoveCallback(CommandCallback callback) {
        serverCommandListeners.put("onMove", callback);
    }

    public void setTurnCallback(CommandCallback callback) {
        serverCommandListeners.put("onTurn", callback);
    }

    /**
     * The type Response challenge.
     */
    public static class ResponseChallenge {
        private String challenger;
        private int number;
        private String gameType;
        private ChallengeStatus status;

        /**
         * Instantiates a new Response challenge.
         */
        public ResponseChallenge(String challenger, int number, String gameType, ChallengeStatus status) {
            this.challenger = challenger;
            this.number = number;
            this.gameType = gameType;
            this.status = status;
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

        public void setStatus(ChallengeStatus status) {
            this.status = status;
        }

        public ChallengeStatus getStatus() {
            return status;
        }
    }
}
