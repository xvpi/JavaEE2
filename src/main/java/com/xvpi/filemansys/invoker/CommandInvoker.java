package com.xvpi.filemansys.invoker;

import com.xvpi.filemansys.command.Command;

public class CommandInvoker {
    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    public void executeCommand() {
        if (command != null) {
            command.execute();
        }
    }
}

