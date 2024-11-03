package com.xvpi.filemansys.command;

import com.xvpi.filemansys.receiver.FileManager;

public class CreateFileCommand implements Command {
    private FileManager fileManager;
    private String fileName;

    public CreateFileCommand(FileManager fileManager, String fileName) {
        this.fileManager = fileManager;
        this.fileName = fileName;
    }

    @Override
    public void execute() {
        if (fileManager.createFile(fileName)) {
            System.out.println("文件创建成功: " + fileName);
        } else {
            System.out.println("文件创建失败: " + fileName);
        }
    }
}
