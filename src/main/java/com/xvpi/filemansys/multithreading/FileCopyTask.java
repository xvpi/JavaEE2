package com.xvpi.filemansys.multithreading;

import com.xvpi.filemansys.logger.Logger;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.*;

public class FileCopyTask implements Runnable {
    private String sourceFile;
    private String destinationFile;
    private ProgressListener progressListener;
    private Logger logger;
    private int lastLoggedProgress = -1;

    public FileCopyTask(String sourceFile, String destinationFile, ProgressListener progressListener, Logger logger) {
        this.sourceFile = sourceFile;
        this.destinationFile = destinationFile;
        this.progressListener = progressListener;
        this.logger = logger;
    }

    @Override
    public void run() {
        Path sourcePath = Paths.get(sourceFile);
        Path destinationPath = Paths.get(destinationFile);

        try (FileChannel sourceChannel = FileChannel.open(sourcePath, StandardOpenOption.READ);
             FileChannel destinationChannel = FileChannel.open(destinationPath, StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {

            long totalBytes = sourceChannel.size();
            long bytesCopied = 0;
            ByteBuffer buffer = ByteBuffer.allocate(1024);

            while (sourceChannel.read(buffer) > 0) {
                buffer.flip();
                while (buffer.hasRemaining()) {
                    bytesCopied += destinationChannel.write(buffer);
                }
                buffer.clear();

                // 更新进度并控制日志频率
                int progress = (int) ((bytesCopied * 100) / totalBytes);
                if (progressListener != null) {
                    progressListener.onProgressUpdate(progress);
                }

                // 每增加 5% 的进度记录一次日志
                if (progress - lastLoggedProgress >= 5) {
                    logger.log("当前进度: " + progress + "%");
                    lastLoggedProgress = progress;
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
