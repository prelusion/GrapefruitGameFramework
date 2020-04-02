package com.grapefruit.gamework.framework.network;

import org.junit.Test;

public class CommandTest {

    @Test
    public void tryCommand(){
        ServerManager manager = new ServerManager("217.120.56.152");
        CommandCallback callback = new CommandCallback() {
            @Override
            public void onResponse(String[] args) {
                System.out.println("Response received!");
                if (args != null) {
                    System.out.println("Returned arguments were:");
                    for (String arg: args){
                        System.out.println("-" + arg);
                    }
                } else {
                    System.out.println("No further arguments returned.");
                }
            }
        };
        manager.queueCommand(Commands.login("Jaapie", callback));
        try {
            Thread.sleep(1000);
        }
        catch(InterruptedException e){
            System.out.println("Interrupted");
        }
    }

}
