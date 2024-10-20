package com.xvpi.filemansys.command;
import com.xvpi.filemansys.receiver.FileManager;
public class CreateDirectoryCommand implements Command {
    private FileManager fileManager;
    private String dirName;

    public CreateDirectoryCommand(FileManager fileManager, String dirName) {
        this.fileManager = fileManager;
        this.dirName = dirName;
    }

    @Override
    public void execute() {
        fileManager.createDirectory(dirName);
    }
}
