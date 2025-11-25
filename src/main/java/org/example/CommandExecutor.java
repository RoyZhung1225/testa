package org.example;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public interface CommandExecutor {

    void execute(String[] args) throws IOException, ExecutionException, InterruptedException;
}
