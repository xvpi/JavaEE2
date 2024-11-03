package com.xvpi.filemansys.command;

import com.xvpi.filemansys.receiver.FileManager;

public class OpenFileCommand implements Command {
    private FileManager fileManager;
    private String fileName;

    public OpenFileCommand(FileManager fileManager, String fileName) {
        this.fileManager = fileManager;
        this.fileName = fileName;
    }

    @Override
    public void execute() {
        String content = fileManager.openFile(fileName);
        if (content != null) {
            System.out.println("文件内容:\n" + content);
        } else {
            System.out.println("打开文件失败: " + fileName);
        }
    }
}
