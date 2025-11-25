package org.example.cmd;

import org.example.CommandExecutor;
import org.example.Main;

import java.io.IOException;

public class Help implements CommandExecutor {
    @Override
    public void execute(String[] args) throws IOException {Main.cmdManager.show();}
}
