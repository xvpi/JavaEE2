package com.xvpi.filemansys.command;

import com.xvpi.filemansys.receiver.FileManager;

public class ListFilesCommand implements Command {
    private FileManager fileManager;

    public ListFilesCommand(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    @Override
    public void execute() {
        fileManager.listFiles();
    }
}

