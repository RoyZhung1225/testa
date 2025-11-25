package org.example.cmd;

import com.chenyo.tool.SerialPortToolkit;
import org.example.CommandExecutor;
import org.example.Main;
import org.example.PortConfig;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

public class Write implements CommandExecutor {



    @Override
    public void execute(String[] args) throws IOException, ExecutionException, InterruptedException {
        if(args.length < 2) {
            System.out.println("Usage: write <portName> <Data>");
            return;
        }
        String portName = args[0];
        String[] newArgs = Arrays.copyOfRange(args, 1, args.length);
        PortConfig config = Main.portManager.getConfigMap().get(portName);
        if(config == null){
            System.err.println("config not found");
            return;
        }

        String msg = String.join( " ", newArgs) + "\n";

        ByteBuffer buffer = StandardCharsets.UTF_8.encode(msg);
        SerialPortToolkit toolkit = config.getToolkit();

        if(toolkit.isWriting()){
            System.out.println("請放緩你的寫入速度");
        }else {
            if((config.getFutureWrite() == null)){
                config.setFutureWrite(toolkit.write(buffer));
                return;
            }

            if(!config.getFutureWrite().isDone()){
                System.out.println("writeBusy");
                return;
            }

            config.setFutureWrite(toolkit.write(buffer));
        }


    }
}
