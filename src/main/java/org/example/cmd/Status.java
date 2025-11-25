package org.example.cmd;

import org.example.CommandExecutor;
import org.example.Main;

import java.io.IOException;

public class Status implements CommandExecutor {
    @Override
    public void execute(String[] args) throws IOException {
        Main.portManager.showStatus();
    }
}
