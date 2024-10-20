package com.xvpi.filemansys.command;

import com.xvpi.filemansys.receiver.FileManager;

public class CompressCommand implements Command {
    private FileManager fileManager;
    private String sourceDirectoryName;
    private String zipFileName;

    public CompressCommand(FileManager fileManager, String sourceDirectoryName, String zipFileName) {
        this.fileManager = fileManager;
        this.sourceDirectoryName = sourceDirectoryName;
        this.zipFileName = zipFileName;
    }

    @Override
    public void execute() {
        fileManager.compress(sourceDirectoryName, zipFileName);
    }
}
