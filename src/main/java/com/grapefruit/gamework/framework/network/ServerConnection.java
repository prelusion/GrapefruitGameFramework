package com.grapefruit.gamework.framework.network;

import com.google.gson.Gson;
import com.grapefruit.gamework.app.resources.AppSettings;

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
        socket = new Socket(serverIp, AppSettings.getSettings().getPort());
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
            System.out.println("SVR GAME CHALLENGE CANCELLED, number: " + number);
            manager.getChallenges().removeIf(challenge -> challenge.getNumber() == number);

        } else if (msg.startsWith("SVR GAME CHALLENGE")) {
            CommandCallback listener = serverCommandListeners.get("onNewChallenge");
            String challengeNumber = parseCommandArg(msg, "CHALLENGENUMBER");
            System.out.println("SVR GAME CHALLENGE, number: " + challengeNumber);
            if (listener != null) {
                listener.onResponse(true, new String[]{challengeNumber});
            } else {
                ResponseChallenge challenge = parseChallenge(msg);
                challenge.setStatus(ChallengeStatus.CHALLENGE_RECEIVED);
                manager.addChallenge(challenge);
            }

        } else if (msg.startsWith("SVR GAME MATCH")) {
            // SVR GAME MATCH {PLAYERTOMOVE: "jarno", GAMETYPE: "Reversi", OPPONENT: "bob"}
            String firstTurnName = parseCommandArg(msg, "PLAYERTOMOVE");
            String opponentName = parseCommandArg(msg, "OPPONENT");
            System.out.println("SVR GAME MATCH, first turn: " + firstTurnName + ", opponent: " + opponentName);
            CommandCallback listener = serverCommandListeners.get("onStartGame");
            if (listener != null) {
                listener.onResponse(true, new String[]{firstTurnName, opponentName});
            }

        } else if (msg.startsWith("SVR GAME YOURTURN")) {
            // SVR GAME YOURTURN {TURNMESSAGE: ""}
            String message = parseCommandArg(msg, "TURNMESSAGE");
            System.out.println("SVR GAME YOURTURN, message: " + message);
            CommandCallback listener = serverCommandListeners.get("onTurn");
            if (listener != null) {
                listener.onResponse(true, new String[]{message});
            }

        } else if (msg.startsWith("SVR GAME MOVE")) {
            // SVR GAME MOVE {PLAYER: "alice", MOVE: "26", DETAILS: ""}
            String playerName = parseCommandArg(msg, "PLAYER");
            String move = parseCommandArg(msg, "MOVE");
            String details = parseCommandArg(msg, "DETAILS");

            int[] rowcol = Helpers.convertMoveString(move, 8);

            System.out.println("SVR GAME MOVE, player: " + playerName + ", details: " + details + ", move index: " + move + "move rowcol: " + rowcol[0] + "," + rowcol[1] );

            CommandCallback listener = serverCommandListeners.get("onMove");
            if (listener != null) {
                listener.onResponse(
                        true,
                        new String[]{playerName, move, details}
                );
            }
        } else if (msg.startsWith("SVR GAME LOSS")) {
            // SVR GAME LOSS {PLAYERONESCORE: "0", PLAYERTWOSCORE: "0", COMMENT: "Turn timelimit reached"}
            String playerOneScore = parseCommandArg(msg, "PLAYERONESCORE");
            String playerTwoScore = parseCommandArg(msg, "PLAYERTWOSCORE");
            String comment = parseCommandArg(msg, "COMMENT");

            System.out.println("SVR GAME LOSS, comment: " + comment);

//            System.out.println("SVR GAME LOSS");
//            System.out.println("Comment: " + comment);

            if (comment.equals("Turn timelimit reached")) {
                CommandCallback listener = serverCommandListeners.get("onTurnTimeoutLose");
                if (listener != null) listener.onResponse(true, new String[]{});
            } else if (comment.equals("Illegal move")) {
                CommandCallback listener = serverCommandListeners.get("onIllegalMoveLose");
                if (listener != null) listener.onResponse(true, new String[]{});
            }
        } else if (msg.startsWith("SVR GAME WIN")) {
            String playerOneScore = parseCommandArg(msg, "PLAYERONESCORE");
            String playerTwoScore = parseCommandArg(msg, "PLAYERTWOSCORE");
            String comment = parseCommandArg(msg, "COMMENT");

            System.out.println("SVR GAME WIN, comment: " + comment);

            if (comment.equals("Turn timelimit reached")) {
                CommandCallback listener = serverCommandListeners.get("onTurnTimeoutWin");
                if (listener != null) {
                    listener.onResponse(true, new String[]{});
                }
            } else if (comment.equals("Illegal move")) {
                CommandCallback listener = serverCommandListeners.get("onIllegalmoveWin");
                if (listener != null) listener.onResponse(true, new String[]{});
            } else if (comment.equals("Player forfeited match")) {
                CommandCallback listener = serverCommandListeners.get("onPlayerForfeit");
                if (listener != null) listener.onResponse(true, new String[]{});
            } else if (comment.equals("Client disconnected")) {
                CommandCallback listener = serverCommandListeners.get("onPlayerDisconnect");
                if (listener != null) listener.onResponse(true, new String[]{});
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
            String keyword = msg.split(" ")[1].split(" ")[0];

            int startArg = msg.indexOf("[");
            String[] args = msg.substring(startArg + 1, msg.trim().length() - 1).split(", ");

            String[] result = new String[args.length];
            int i = 0;

            for (String element : args) {
                try {
                    result[i] = element.substring(1, element.length() - 1);
                } catch (StringIndexOutOfBoundsException e) {
                    e.printStackTrace();
                    Command command = manager.findByKeyword(keyword, true);
                    if (command != null && command.isSent()) {
                        manager.removeCommandFromQueue(command);
                    }
                    return;
                }
                i++;
            }

            Command command = manager.findByKeyword(keyword, true);

            if (command != null && command.isSent()) {
                command.confirm();
                command.doCallBack(true, result);
                manager.removeCommandFromQueue(command);
            }
        }
    }

    private String parseCommandArg(String msg, String fieldname) {
//        System.out.println(fieldname + ": \"");
        String a = msg.split(fieldname + ": \"")[1];
        String b = a.split("\"")[0];
        return b;
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
        socket.close();
        in.close();
        out.close();

//        stillconnected = false;
//        boolean stillconnected = true;
//        int i = 0;
//        while (stillconnected || i > 5) {
//            System.out.println("while loop");
//            if (!listenerThread.isAlive() && !timer.isAlive()) {
//                socket.close();
//                in.close();
//                out.close();
//                stillconnected = false;
//            }
//
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException ignored) {}
//
//            i++;
//        }
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
                        Thread.sleep(100);
                    } catch (Exception e) {
                        Thread.currentThread().interrupt();
                    }
                }
                System.out.println("Start sending interrupted");
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

    public void setTurnTimeoutWinCallback(CommandCallback callback) {
        serverCommandListeners.put("onTurnTimeoutWin", callback);
    }

    public void setTurnTimeoutLoseCallback(CommandCallback callback) {
        serverCommandListeners.put("onTurnTimeoutLose", callback);
    }

    public void setIllegalmoveWinCallback(CommandCallback callback) {
        serverCommandListeners.put("onIllegalmoveWin", callback);
    }

    public void setOnPlayerForfeitCallback(CommandCallback callback) {
        serverCommandListeners.put("onPlayerForfeit", callback);
    }

    public void setOnPlayerDisconnectCallback(CommandCallback callback) {
        serverCommandListeners.put("onPlayerDisconnect", callback);
    }

    public void setOnNewChallengetCallback(CommandCallback callback) {
        serverCommandListeners.put("onNewChallenge", callback);
    }

    public void setIllegalmoveLoseCallback(CommandCallback callback) {
        serverCommandListeners.put("onIllegalMoveLose", callback);
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
