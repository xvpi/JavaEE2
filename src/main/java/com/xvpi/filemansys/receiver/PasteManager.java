package com.xvpi.filemansys.receiver;

import com.xvpi.filemansys.logger.Logger;
import com.xvpi.filemansys.strategy.FileUtils;

import java.io.*;

public class PasteManager {
    //前台粘贴
    public void pasteFileWithProgress(String sourcePath, String targetPath, boolean showProgress,  Logger logger) {
        File source = new File(sourcePath);
        File target = new File(targetPath);

        if (source.isDirectory()) {
            // 处理文件夹的粘贴
            pasteDirectoryWithProgress(source, target, showProgress, logger);
        } else {
            // 处理文件的粘贴
            try (InputStream in = new FileInputStream(source);
                 OutputStream out = new FileOutputStream(target)) {

                long totalBytes = source.length();
                long bytesCopied = 0;
                byte[] buffer = new byte[1024];
                int bytesRead;

                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                    bytesCopied += bytesRead;

                    if (showProgress) {
                        int progress = (int) ((bytesCopied * 100) / totalBytes);
                        FileUtils.updateProgressBar(progress);
                    }
                }

                if (showProgress) {
                    System.out.println("\n拷贝完成!");
                    // 实现文件粘贴的逻辑，同时在需要的地方记录日志
                    logger.log("粘贴文件: " + sourcePath + " 到 " + targetPath);
                }

            } catch (IOException e) {
                System.out.println("文件拷贝失败: " + e.getMessage());
            }
        }
    }

    private void pasteDirectoryWithProgress(File sourceDir, File targetDir, boolean showProgress,Logger logger) {
        if (!targetDir.exists()) {
            targetDir.mkdirs(); // 创建目标文件夹
        }

        File[] files = sourceDir.listFiles();
        if (files != null) {
            for (File file : files) {
                File targetFile = new File(targetDir, file.getName());
                if (file.isDirectory()) {
                    pasteDirectoryWithProgress(file, targetFile, showProgress,logger); // 递归处理子文件夹
                } else {
                    pasteFileWithProgress(file.getAbsolutePath(), targetFile.getAbsolutePath(), showProgress,logger); //
                    // 处理文件的粘贴
                }
            }
        }
    }
}
