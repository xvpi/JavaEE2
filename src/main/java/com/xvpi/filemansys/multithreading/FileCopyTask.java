package com.xvpi.filemansys.multithreading;

import com.xvpi.filemansys.logger.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.*;

public class FileCopyTask implements Runnable {
    private String sourceFile;
    private String destinationFile;
    private ProgressListener progressListener;
    private Logger logger; // 添加 Logger
    private int lastLoggedProgress = -1; // 跟踪上次记录的进度

    public FileCopyTask(String sourceFile, String destinationFile, ProgressListener progressListener, Logger logger) {
        this.sourceFile = sourceFile;
        this.destinationFile = destinationFile;
        this.progressListener = progressListener;
        this.logger = logger; // 初始化 Logger
    }

    @Override
    public void run() {
        try {
            Path sourcePath = Paths.get(sourceFile);
            Path destinationPath = Paths.get(destinationFile);
            long totalBytes = Files.size(sourcePath);
            long bytesCopied = 0;

            try (InputStream in = Files.newInputStream(sourcePath);
                 OutputStream out = Files.newOutputStream(destinationPath)) {
                byte[] buffer = new byte[1024];
                int bytesRead;

                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                    bytesCopied += bytesRead;

                    // 更新进度
                    if (progressListener != null) {
                        int progress = (int) ((bytesCopied * 100) / totalBytes);
                        progressListener.onProgressUpdate(progress);


                        // 仅在每5%进度更新时记录日志
                        if (progress - lastLoggedProgress >= 5) {
                            logger.log("当前进度: " + progress + "%");
                            lastLoggedProgress = progress; // 更新最后记录的进度

                        }
                    }
                }
            }
            System.out.println("文件复制成功: " + destinationFile);
            logger.log("文件复制成功: " + destinationFile);
        } catch (IOException e) {
            System.out.println("文件复制失败: " + e.getMessage());
            logger.log("文件复制失败: " + e.getMessage());
        }
    }
}
