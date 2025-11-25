package org.example;

import com.chenyo.tool.SerialPortToolkit;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class PortConfig {

    @Getter
    SerialPortToolkit toolkit = new SerialPortToolkit();
    @Setter
    @Getter
    Future<Integer> futureRead;
    @Setter
    @Getter
    Future<Integer> futureWrite;
    @Setter
    private LogOutPut logger;
    @Setter
    long futureStart;
    long futureEnd;

    public StringBuilder readBuffer = new StringBuilder();

    public PortConfig(){}

    private void log(String msg) {
        if (logger != null) logger.log(msg);
    }

    public void getParity(String portName){
        int parity =  this.toolkit.findPort(portName).getParity();
        switch (parity) {
            case 0 -> {
                log("Parity: NONE");
                System.out.println("Parity: NONE");
            }
            case 1 -> {
                log("Parity: ODD");
                System.out.println("Parity: ODD");
            }
            case 2 -> {
                log("Parity: EVEN");
                System.out.println("Parity: EVEN");
            }
            case 3 -> {
                log("Parity: MARK");
                System.out.println("Parity: MARK");
            }
            case 4 -> {
                log("Parity: SPACE");
                System.out.println("Parity: SPACE");
            }
            default -> {
                log("Unknown parity");
                System.err.println("Unknown parity");
            }
        }
    }

    public void showStatus(){
        log("PortName:" + toolkit.getPortName());
        log("BaudRate:" + toolkit.getBaudRate());
        System.out.println("PortName:" + toolkit.getPortName());
        System.out.println("BaudRate:" + toolkit.getBaudRate());
        getParity(toolkit.getPortName());
        log("-------------------");
        System.out.println("-------------------");

    }

    public void readData(int available, ByteBuffer buffer) throws ExecutionException, InterruptedException, IOException {
        if(available <= 0)
            return;
        byte[] data = new byte[available];
        buffer.get(data);
        for (byte datum : data) {
            char c = (char) datum;

            readBuffer.append(c);

            if (c == '\n') {
                String message = readBuffer.toString().trim(); // 去掉 \n

                log(toolkit.getPortName() + "讀取訊息:" + message);
                System.out.println(toolkit.getPortName() + "讀取訊息:" + message);

                // 清空緩衝器（準備下個訊息）
                readBuffer.setLength(0);
            }

        }


    }
}
