package com.xvpi.filemansys.command;

import com.xvpi.filemansys.receiver.FileManager;

import java.io.File;

public class CopyDirectoryCommand implements Command {
    private FileManager fileManager;
    private String sourceDirectoryName;
    private String destinationDirectoryName;

    public CopyDirectoryCommand(FileManager fileManager, String sourceDirectoryName, String destinationDirectoryName) {
        this.fileManager = fileManager;
        this.sourceDirectoryName = sourceDirectoryName;
        this.destinationDirectoryName = destinationDirectoryName;
    }

    @Override
    public void execute() {
        File sourceDirectory = new File(fileManager.getCurrentDirectory(), sourceDirectoryName);
        File destinationDirectory = new File(fileManager.getCurrentDirectory(), destinationDirectoryName);
        fileManager.copyDirectory(sourceDirectory, destinationDirectory);
    }
}