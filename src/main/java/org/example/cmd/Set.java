package org.example.cmd;

import org.example.CommandExecutor;
import org.example.LogOutPut;
import org.example.Main;

import java.io.IOException;

public class Set implements CommandExecutor {

    LogOutPut logger;

    public Set(LogOutPut logger){
        this.logger = logger;
    }

    public void log(String msg){
        if (logger != null) {
            logger.log(msg);
        }
    }


    @Override
    public void execute(String[] args) throws IOException {
        if(args.length == 0 | args.length < 3){
            log("Usage: parm set <portName> <key> <value>");
        }
        String key = args[2].toLowerCase();
        String portName = args[1].toUpperCase();
        int baud = Integer.parseInt(args[3]);
        Main.portManager.getConfigMap().get(portName).getToolkit().setBaudRate(baud);

        if (key.equals("baud")) {
            log("Set BaudRate:" + baud);
        }else {
            System.err.println("unKnownCommand!");
        }
    }
}
