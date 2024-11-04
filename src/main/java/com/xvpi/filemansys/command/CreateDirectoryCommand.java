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
        if (fileManager.createDirectory(dirName)) {
            System.out.println("文件夹创建成功: " + dirName);
        } else {
            System.out.println("文件夹创建失败: " + dirName);
        }
    }
}
