package org.example;

import com.chenyo.tool.SerialPortToolkit;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static PortManager portManager = new PortManager();
    public static CmdManager cmdManager = new CmdManager();

    public static void main(String[] args) {
        cmdManager.registerCommand();
        GUI gui = new GUI(cmdManager);
        cmdManager.setLogger(gui);
        portManager.setLogger(gui);
        Scanner sc = new Scanner(System.in);
        ByteBuffer buffer = null;

        while (true) {
            try {
                if (System.in.available() > 0) {
                    String input = sc.nextLine();
                    if (input.isEmpty()) {
                        continue;
                    }
                    cmdManager.execute(input);
                }
            } catch (IOException | ExecutionException | InterruptedException e) {
                System.err.println("錯誤" + e);
            }
            try {
                if (portManager.configMap.isEmpty()) {
                    continue;
                }
                for (Map.Entry<String, PortConfig> entry : portManager.configMap.entrySet()) {
                    PortConfig config = entry.getValue();
                    Future<Integer> f = config.futureRead;
                    SerialPortToolkit kit = config.getToolkit();
                    if (f == null) {
                        config.toolkit.onClearReadBuffer();
                        buffer = ByteBuffer.allocate(4096);
                        config.futureStart = System.currentTimeMillis();
                        config.setFutureRead(config.toolkit.read(buffer));
                        continue;
                    }
                    if (f.isDone()) {
                        config.futureEnd = System.currentTimeMillis();
                        long readDuration = config.futureEnd - config.futureStart;
                        int available = f.get();
                        if (available <= 0) {
                            config.toolkit.onClearReadBuffer();
                            config.futureStart = System.currentTimeMillis();
                            config.setFutureRead(config.toolkit.read(buffer));
                            continue;
                        }
                        kit.setReadTimeout((int) readDuration);
                        config.readData(available, buffer);
                        config.toolkit.onClearReadBuffer();
                        buffer = ByteBuffer.allocate(4096);
                        config.futureStart = System.currentTimeMillis();
                        config.setFutureRead(config.toolkit.read(buffer));
                    }
                }
            } catch (ExecutionException e) {
                System.err.println("EXE");
            } catch (InterruptedException e) {
                System.err.println("INT");
            } catch (IOException e) {
                System.err.println("IO");
            }
        }
    }


}