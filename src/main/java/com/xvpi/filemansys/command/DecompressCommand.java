package com.xvpi.filemansys.command;

import com.xvpi.filemansys.receiver.FileManager;

public class DecompressCommand implements Command {
    private FileManager fileManager;
    private String zipFileName;
    private String destinationDirectoryName;

    public DecompressCommand(FileManager fileManager, String zipFileName, String destinationDirectoryName) {
        this.fileManager = fileManager;
        this.zipFileName = zipFileName;
        this.destinationDirectoryName = destinationDirectoryName;
    }

    @Override
    public void execute() {
        fileManager.decompress(zipFileName, destinationDirectoryName);
    }
}