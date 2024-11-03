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
        if (fileManager.deleteFile(fileName)) {
            System.out.println("文件删除成功: " + fileName);
        } else {
            System.out.println("文件删除失败: " + fileName);
        }
    }
}

