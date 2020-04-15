package com.grapefruit.gamework.network;

import org.junit.Test;

public class ServerProtocolTest {

    @Test
    public void tryCommand(){
        ServerManager manager = new ServerManager();
        manager.connect("217.120.56.152");
        CommandCallback callback = new CommandCallback() {
            @Override
            public void onResponse(boolean success, String[] args) {
                if (success) {
                    System.out.println("Response received!");
                    if (args != null) {
                        System.out.println("Returned arguments were:");
                        for (String arg : args) {
                            System.out.println("-" + arg);
                        }
                    } else {
                        System.out.println("No further arguments returned.");
                    }

                } else {
                    System.out.println("Command failed!");
                    System.out.println(args[0]);
                }
            }
        };
        manager.queueCommand(Commands.login("Delano", callback));
        manager.queueCommand(Commands.login("Rick", callback));
        manager.queueCommand(Commands.login("Jarno", callback));
        manager.queueCommand(Commands.login("Leon", callback));
        manager.queueCommand(Commands.getGameList(callback));
        manager.queueCommand(Commands.getHelp(callback, "login"));
        manager.queueCommand(Commands.logout(callback));

        try {
            Thread.sleep(1000);
        }
        catch(InterruptedException e){
            System.out.println("Interrupted");
        }
        manager.disconnect();
        System.out.println("closed");
    }

}
