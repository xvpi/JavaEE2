package com.xvpi.filemansys.command;

import com.xvpi.filemansys.receiver.CompressionManager;


public class CompressCommand implements Command {

    private CompressionManager compressManager;
    private String sourceDir;
    private String zipFileName;

    public CompressCommand(CompressionManager compressManager, String sourceDir,
                           String zipFileName) {
        this.compressManager = compressManager;
        this.sourceDir = sourceDir;
        this.zipFileName = zipFileName;
    }

    @Override
    public void execute() {
        if (compressManager.compress(sourceDir, zipFileName)) {
            System.out.println("文件夹压缩成功: " + zipFileName);
        } else {
            System.out.println("文件夹压缩失败，未找到路径下的文件: " + sourceDir);
        }
    }
}