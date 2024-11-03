package com.xvpi.filemansys.command;

import com.xvpi.filemansys.receiver.FileManager;

public class DecompressCommand implements Command {
    private FileManager fileManager;
    private String sourceDir;
    private String destinationDirectoryName;

    public DecompressCommand(FileManager fileManager, String sourceDir, String destinationDirectoryName) {
        this.fileManager = fileManager;
        this.sourceDir = sourceDir;
        this.destinationDirectoryName = destinationDirectoryName;
    }

    @Override
    public void execute() {
        if (fileManager.decompress(sourceDir, destinationDirectoryName)) {
            System.out.println("文件解压成功: " + destinationDirectoryName);
        } else {
            System.out.println("文件解压失败，找不到源文件: " + sourceDir);
        }
    }
}