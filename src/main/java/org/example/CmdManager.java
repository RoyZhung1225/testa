package org.example;

import lombok.Setter;
import org.example.cmd.*;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class CmdManager {
    LinkedHashMap <String, CommandExecutor> cmdMap = new LinkedHashMap<>();
    @Setter
    private LogOutPut logger;
    public void register(String cmd, CommandExecutor commandExecutor){this.cmdMap.put(cmd.toLowerCase(), commandExecutor);}

    public void registerCommand(){
        register("open", new OpenPort(logger));
        register("write", new Write());
        register("Parm", new Parameter(logger));
        register("status", new Status());
        register("close", new ClosePort(logger));
        register("help", new Help());
    }
    public void registerGS(){
        register("get", new Get(logger));
        register("set", new Set(logger));
    }

    public void execute(String input) throws IOException, ExecutionException, InterruptedException {
        String[] parts = input.trim().split("\\s+");

        if(parts.length == 0){
            System.err.println("異常輸入!");
            log("異常輸入: " + input);
            return;
        }

        String cmdName = parts[0].toLowerCase();
        String[] args = new String[parts.length - 1];
        try{
            System.arraycopy(parts, 1, args, 0, args.length);
        }catch (Exception e){
            e.printStackTrace();
        }
        CommandExecutor c = cmdMap.get(cmdName);
        if (c != null) {
            c.execute(args);
        }else {
            log("查無此指令!" + cmdName);
            System.out.println("查無此指令!" + cmdName);
        }
    }
    public void show(){
        log("可用指令:");
        System.out.println("可用指令:");
        for(Map.Entry<String, CommandExecutor> entry : cmdMap.entrySet()){
            log(entry.getKey());
            System.out.println(entry.getKey());
        }
    }

    public CommandExecutor getExe(String s){
        try{
            return this.cmdMap.get(s);
        }catch (NullPointerException e){
            System.err.println("查無此指令!");
            return null;
        }
    }
    public void log(String msg) {
        if (logger != null) logger.log(msg);
    }


}
