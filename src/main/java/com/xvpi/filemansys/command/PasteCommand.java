package com.xvpi.filemansys.command;

import com.xvpi.filemansys.multithreading.FileCopyTask;

import com.xvpi.filemansys.logger.FileLogger;
import com.xvpi.filemansys.logger.LogDecorator;
import com.xvpi.filemansys.receiver.PasteManager;
public class PasteCommand implements Command {
    private PasteManager pasteManager;
    private String sourceFilePath;
    private String targetFilePath;
    private boolean isBackground;
    private LogDecorator logger;

        public PasteCommand(PasteManager pasteManager, String sourceFilePath, String targetFilePath,
                            boolean isBackground) {
            this.pasteManager = pasteManager;
            this.sourceFilePath = sourceFilePath;
            this.targetFilePath = targetFilePath;
            this.isBackground = isBackground;
            this.logger = new LogDecorator(new FileLogger("operation_log.txt")); // 指定日志文件路径
        }

        @Override
        public void execute() {
            logger.log("开始执行粘贴命令: 源路径 = " + sourceFilePath + ", 目标路径 = " + targetFilePath);
            if (isBackground) {
                new Thread(new FileCopyTask(sourceFilePath, targetFilePath, progress -> {
                    //System.out.print("\r进度: " + progress + "%");

                }, logger)).start(); // 传递 logger
                logger.log("文件正在后台拷贝...");
            } else {
                // 同步执行，并传入 logger 记录日志
                pasteManager.pasteFileWithProgress(sourceFilePath, targetFilePath, true, logger); // 确保这里也能处理 logger
                logger.log("文件粘贴完成。");
            }
        }
    }