package org.example;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;

public class PortManager {
    @Getter
    Map<String, PortConfig> configMap = new LinkedHashMap<>();
    @Setter
    private LogOutPut logger;

    private void log(String msg) {
        if (logger != null) logger.log(msg);
    }

    public PortManager() {
    }

    public void open(String portName) {
        PortConfig config = new PortConfig();
        config.setLogger(logger);
        config.toolkit.setSerialPort(portName);
        if (config.toolkit.findPort(portName) == null) {
            log("Port does not exist!");
            System.out.println("Port does not exist!");
            return;
        } /* 如果port已被開啟 */
        if (!config.toolkit.onConnect()) {
            log("Port is already opened!");
            System.err.println("Port is already opened!");
            return;
        }
        log(portName + "is open");
        System.out.println(portName + "open");
        configMap.put(portName, config);
    }

    /**
     * * @param portName 要關閉的port
     */
    public void close(String portName) {
        PortConfig config = configMap.get(portName);
        if (config == null) {
            log(portName + "先前已關閉!");
            System.err.println(portName + "先前已關閉!");
            return;
        }
        System.out.println(config.toolkit.getPortName());
        if (config.futureRead == null | config.futureWrite == null) {
            log("沒有待解決的任務");
            System.out.println("沒有待解決的任務");
        } else {
            config.futureWrite.cancel(true);
            config.futureRead.cancel(true);
        }
        config.toolkit.onDisconnect();
    }

    public void showStatus() {
        for (Map.Entry<String, PortConfig> entry : configMap.entrySet()) {
            entry.getValue().showStatus();
        }
    }

    public int getBaudRate(String portName) {
        return this.configMap.get(portName).toolkit.getBaudRate();
    }

    public void getParity(String portName) {
        int parity = this.configMap.get(portName).toolkit.findPort(portName).getParity();
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
}