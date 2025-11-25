package org.example.cmd;

import org.example.CommandExecutor;
import org.example.LogOutPut;
import org.example.Main;

import java.io.IOException;

public class ClosePort implements CommandExecutor {
    LogOutPut logger;

    public ClosePort(LogOutPut logger){
        this.logger = logger;
    }

    public void log(String msg){
        if (logger != null) {
            logger.log(msg);
        }
    }
    @Override
    public void execute(String[] args) throws IOException {
        if(args.length == 0) {
            log("Usage: close <portName>");

        }else {
            String portName = args[0];
            Main.portManager.close(portName);
        }
    }
}
