package com.xvpi.filemansys.command;

import com.xvpi.filemansys.receiver.FileManager;

public class ListFilesCommand implements Command {
    private FileManager fileManager;
    private String seq;

    public ListFilesCommand(FileManager fileManager,String seq) {
        this.fileManager = fileManager;
        this.seq = seq;
    }

    @Override
    public void execute() {
        fileManager.listFiles(seq);
    }
}

