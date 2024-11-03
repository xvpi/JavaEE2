package com.xvpi.filemansys.command;

import com.xvpi.filemansys.receiver.FileManager;


public class PasteCommand implements Command {
    private FileManager fileManager;
    private String sourceFilePath;
    private String targetFilePath;
    private boolean isBackground;

    public PasteCommand(FileManager fileManager, String sourceFilePath, String targetFilePath, boolean isBackground) {
        this.fileManager = fileManager;
        this.sourceFilePath = sourceFilePath;
        this.targetFilePath = targetFilePath;
        this.isBackground = isBackground;
    }

    @Override
    public void execute() {
        if (isBackground) {
            // 异步后台执行拷贝任务
            new Thread(() -> fileManager.pasteFileWithProgress(sourceFilePath, targetFilePath, false)).start();
            System.out.println("文件正在后台拷贝...");
        } else {
            // 同步前台执行拷贝任务并显示进度
            fileManager.pasteFileWithProgress(sourceFilePath, targetFilePath, true);
        }
    }
}
