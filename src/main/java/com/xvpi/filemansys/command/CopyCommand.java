package com.xvpi.filemansys.command;

import com.xvpi.filemansys.receiver.FileManager;

public class CopyCommand implements Command {
    private FileManager fileManager;
    private String sourceFile;

    public CopyCommand(FileManager fileManager, String sourceFile) {
        this.fileManager = fileManager;
        this.sourceFile = sourceFile;
    }

    @Override
    public void execute() {
        fileManager.copyFile(sourceFile);
    }
}
