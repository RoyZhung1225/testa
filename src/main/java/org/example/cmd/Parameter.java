package org.example.cmd;

import org.example.CmdManager;
import org.example.CommandExecutor;
import org.example.LogOutPut;
import org.example.LogOutPut;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class Parameter implements CommandExecutor {

    CmdManager cmdManager = new CmdManager();

    LogOutPut logger;

    public Parameter(LogOutPut logger){
        cmdManager.registerGS();
        this.logger = logger;
    }

    public void log(String msg){
        if (logger != null) {
            logger.log(msg);
        }
    }

    @Override
    public void execute(String[] args) throws IOException, ExecutionException, InterruptedException {
        if(args.length == 0) {
            log("Usage: parm <get/set> <portName> <key>");
        }else {
            if(cmdManager.getExe(args[0]) == null){
                log("Usage: parm <get/set> <portName> <key>");
            }else {
                CommandExecutor commandExecutor =  cmdManager.getExe(args[0]);
                commandExecutor.execute(args);
            }
        }
    }
}
