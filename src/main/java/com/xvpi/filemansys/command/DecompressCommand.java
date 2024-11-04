package com.xvpi.filemansys.command;

import com.xvpi.filemansys.exception.FileManagementException;
import com.xvpi.filemansys.receiver.CompressionManager;


public class DecompressCommand implements Command {
    private CompressionManager compressManager;
    private String sourceDir;
    private String destinationDirectoryName;

    public DecompressCommand(CompressionManager compressManager, String sourceDir, String destinationDirectoryName) {
        this.compressManager = compressManager;
        this.sourceDir = sourceDir;
        this.destinationDirectoryName = destinationDirectoryName;
    }

    @Override
    public void execute() {
        try {
            if (compressManager.decompress(sourceDir, destinationDirectoryName)) {
                System.out.println("文件解压成功: " + destinationDirectoryName);
            } else {
                System.out.println("文件解压失败，找不到源文件: " + sourceDir);
            }
        } catch (FileManagementException e) {
            System.out.println("解压失败: " + e.getMessage());
        }
    }
}
