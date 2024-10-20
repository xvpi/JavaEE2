package com.xvpi.filemansys.command;

import com.xvpi.filemansys.receiver.FileManager;

public class CopyFileCommand implements Command {
    private FileManager fileManager;
    private String sourceFile;
    private String destinationFile;

    public CopyFileCommand(FileManager fileManager, String sourceFile, String destinationFile) {
        this.fileManager = fileManager;
        this.sourceFile = sourceFile;
        this.destinationFile = destinationFile;
    }

    @Override
    public void execute() {
        fileManager.copyFile(sourceFile, destinationFile);
    }
}
