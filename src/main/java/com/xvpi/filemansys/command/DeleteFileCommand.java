package com.xvpi.filemansys.command;

import com.xvpi.filemansys.receiver.FileManager;

public class DeleteFileCommand implements Command {
    private FileManager fileManager;
    private String fileName;

    public DeleteFileCommand(FileManager fileManager, String fileName) {
        this.fileManager = fileManager;
        this.fileName = fileName;
    }

    @Override
    public void execute() {
        fileManager.deleteFile(fileName);
    }
}

