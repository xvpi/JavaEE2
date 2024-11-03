package com.xvpi.filemansys.command;

import com.xvpi.filemansys.receiver.FileManager;

public class CompressCommand implements Command {
    private FileManager fileManager;
    private String sourceDir;
    private String zipFileName;

    public CompressCommand(FileManager fileManager, String sourceDir, String zipFileName) {
        this.fileManager = fileManager;
        this.sourceDir = sourceDir;
        this.zipFileName = zipFileName;
    }

    @Override
    public void execute() {
        if (fileManager.compress(sourceDir, zipFileName)) {
            System.out.println("文件夹压缩成功: " + zipFileName);
        } else {
            System.out.println("文件夹压缩失败，未找到路径下的文件: " + sourceDir);
        }
    }
}