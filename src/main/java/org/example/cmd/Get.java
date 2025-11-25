package org.example.cmd;

import org.example.CommandExecutor;
import org.example.LogOutPut;
import org.example.Main;

import java.io.IOException;

public class Get implements CommandExecutor {

    LogOutPut logger;

    public Get(LogOutPut logger){
        this.logger = logger;
    }

    public void log(String msg){
        if (logger != null) {
            logger.log(msg);
        }
    }

    @Override
    public void execute(String[] args) throws IOException {
        try {
            if(args.length == 0 | args.length < 1 | args.length < 2 ){
                log("Usage: parm get <portName> <key>");
            }else {
                String key = args[2].toLowerCase();
                String portName = args[1].toUpperCase();
                int baudRate = Main.portManager.getBaudRate(portName);

                if (key.equals("baud")) {
                    log(String.valueOf(baudRate));
                }else {
                    log("UnknownCommand");
                }
            }

        }catch (NullPointerException e){
            System.out.println("port does not open!");
        }




    }
}
