package com.xvpi.filemansys.command;

import com.xvpi.filemansys.receiver.FileManager;


public class SearchFileCommand implements Command {
    private FileManager fileManager;
    private String searchTerms;

    public SearchFileCommand (FileManager fileManager,String searchTerms) {
        this.fileManager = fileManager;
        this.searchTerms = searchTerms;
    }

    @Override
    public void execute() {
        fileManager.searchFiles(searchTerms);
    }
}

