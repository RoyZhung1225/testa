package org.example.cmd;

import org.example.CommandExecutor;
import org.example.LogOutPut;
import org.example.LogOutPut;
import org.example.Main;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class OpenPort implements CommandExecutor {

    LogOutPut logger;

    public OpenPort(LogOutPut logger){
        this.logger = logger;
    }

    public void log(String msg){
        if (logger != null) {
            logger.log(msg);
        }
    }

    @Override
    public void execute(String[] args) throws IOException, ExecutionException, InterruptedException {
        if(args.length == 0 | args.length > 1 ) {
            log("Usage: open <portName>");
        }else {
            String portName = args[0].toUpperCase();
            Main.portManager.open(portName);
        }

    }
}
