package com.xvpi.filemansys.receiver;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.util.zip.ZipInputStream;
import java.io.File;

public class FileManager {
    private String currentDirectory;

    public FileManager() {
        this.currentDirectory = System.getProperty("user.dir"); // 默认工作目录为当前用户目录
    }

    // 获取当前目录路径
    public String getCurrentDirectory() {
        return currentDirectory;
    }

    // 更改当前目录
    public void setCurrentDirectory(String path) {
        File dir = new File(path);
        if (dir.exists() && dir.isDirectory()) {
            currentDirectory = dir.getAbsolutePath();
        } else {
            System.out.println("路径无效，无法更改工作目录。");
        }
    }

    // 获取文件或文件夹的完整路径
    public String getFilePath(String fileName) {
        File file = new File(currentDirectory, fileName); // 使用当前目录和文件名生成路径
        return file.getAbsolutePath();
    }

    // 创建文件
    public boolean createFile(String fileName) {
        File file = new File(currentDirectory, fileName);
        try {
            if (file.createNewFile()) {
                System.out.println("文件创建成功: " + file.getAbsolutePath());
                return true;
            } else {
                System.out.println("文件已存在: " + file.getAbsolutePath());
                return false;
            }
        } catch (IOException e) {
            System.out.println("文件创建失败: " + e.getMessage());
            return false;
        }
    }

    // 删除文件
    public boolean deleteFile(String fileName) {
        File file = new File(currentDirectory, fileName);
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                System.out.println("文件删除成功: " + file.getAbsolutePath());
                return true;
            } else {
                System.out.println("文件删除失败。");
                return false;
            }
        } else {
            System.out.println("文件不存在。");
            return false;
        }
    }

    // 创建文件夹
    public boolean createDirectory(String dirName) {
        File dir = new File(currentDirectory, dirName);
        if (dir.mkdir()) {
            System.out.println("文件夹创建成功: " + dir.getAbsolutePath());
            return true;
        } else {
            System.out.println("文件夹创建失败或已存在。");
            return false;
        }
    }

    // 删除文件夹
    public boolean deleteDirectory(String dirName) {
        File dir = new File(currentDirectory, dirName);
        if (dir.exists() && dir.isDirectory()) {
            try {
                Files.walk(dir.toPath())
                        .map(Path::toFile)
                        .forEach(File::delete);
                System.out.println("文件夹删除成功: " + dir.getAbsolutePath());
                return true;
            } catch (IOException e) {
                System.out.println("文件夹删除失败: " + e.getMessage());
                return false;
            }
        } else {
            System.out.println("文件夹不存在。");
            return false;
        }
    }

    // 列出当前文件夹内容，支持排序
    public void listFiles(String seq) {
        File dir = new File(currentDirectory);
        File[] files = dir.listFiles();

        if (files != null) {

                if (seq.equals("name")) {
                    // 按名称排序
                    Arrays.sort(files, Comparator.comparing(File::getName));
                } else if (seq.equals("time")) {
                    // 按最近修改时间排序
                    Arrays.sort(files, Comparator.comparingLong(File::lastModified));
                } else if (seq.equals("byte")) {
                    // 按文件大小排序
                    Arrays.sort(files, Comparator.comparingLong(File::length));
                } else {
                    System.out.println("输入错误");
                    return;
                }
             System.out.printf("%-24s\t%-20s\t%-8s\t%-15s\n", "文件名", "最后修改时间", "类型", "大小"); // 表头
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (File file : files) {
                String fileName = file.getName();
                String lastModified = sdf.format(new Date(file.lastModified()));
                String type = file.isDirectory() ? "directory" : "file";
                long size = file.length();
                System.out.printf("%-24s\t%-20s\t%-8s\t%d Bytes\n", fileName, lastModified, type, size);
            }
        } else {
            System.out.println("当前目录不存在或不是一个目录");
        }
    }

    //查找文件
    public void searchFiles(String searchTerm) {
        File dir = new File(currentDirectory);
        File[] files = dir.listFiles();

        if (files != null) {
            // 用于存储匹配的文件
            List<File> matchedFiles = new ArrayList<>();

            // 模糊查找文件
            for (File file : files) {
                if (file.getName().contains(searchTerm)) {
                    matchedFiles.add(file);
                }
            }

            // 如果找到匹配的文件
            if (!matchedFiles.isEmpty()) {
                System.out.printf("%-24s\t%-20s\t%-8s\t%-15s\n", "文件名", "最后修改时间", "类型", "大小"); // 表头
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                for (File file : matchedFiles) {
                    String fileName = file.getName();
                    String lastModified = sdf.format(new Date(file.lastModified()));
                    String type = file.isDirectory() ? "directory" : "file";
                    long size = file.length();
                    System.out.printf("%-24s\t%-20s\t%-8s\t%d Bytes\n", fileName, lastModified, type, size);
                }
            } else {
                System.out.println("没有找到匹配的文件。");
            }
        } else {
            System.out.println("当前目录不存在或不是一个目录");
        }
    }

    // 打开文件内容，显示行号
    public String openFile(String fileName) {
        File file = new File(currentDirectory, fileName);
        if (file.exists() && file.isFile()) {
            StringBuilder content = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                int lineNumber = 1; // 行号从1开始
                while ((line = reader.readLine()) != null) {
                    content.append(String.format("%-5d: %s%n", lineNumber, line)); // 添加行号
                    lineNumber++;
                }
                return content.toString(); // 返回文件内容
            } catch (IOException e) {
                System.out.println("文件读取失败: " + e.getMessage());
                return null;
            }
        } else {
            System.out.println("文件不存在。");
            return null;
        }
    }


    // 拷贝文件
    public boolean copyFile(String sourceFileName, String targetFileName) {
        File sourceFile = new File(currentDirectory, sourceFileName);
        File targetFile = new File(currentDirectory, targetFileName);

        if (!sourceFile.exists()) {
            System.out.println("源文件不存在。");
            return false;
        }

        try {
            Files.copy(sourceFile.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("文件拷贝成功: " + targetFile.getAbsolutePath());
            return true;
        } catch (IOException e) {
            System.out.println("文件拷贝失败: " + e.getMessage());
            return false;
        }
    }

    //前台粘贴
    public void pasteFileWithProgress(String sourcePath, String targetPath, boolean showProgress) {
        File source = new File(sourcePath);
        File target = new File(targetPath);

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

                    // 每完成10%的拷贝，输出一次进度信息
                    if (progress % 10 == 0) {
                        StringBuilder progressBar = new StringBuilder();
                        progressBar.append("\r进度: [");
                        int completed = progress / 10;
                        for (int i = 0; i < 10; i++) {
                            if (i < completed) {
                                progressBar.append("■");
                            } else {
                                progressBar.append("□");
                            }
                        }
                        progressBar.append("] ").append(progress).append("%");
                        System.out.print(progressBar.toString());
                    }
                }
            }

            if (showProgress) {
                System.out.println("\n拷贝完成!");
            }

        } catch (IOException e) {
            System.out.println("文件拷贝失败: " + e.getMessage());
        }
    }



    public boolean compress(String sourceDir, String zipFileName) {
        File dir = new File(sourceDir);
        if (!dir.exists() || !dir.isDirectory()) {
            System.out.println("源文件夹不存在或不是文件夹: " + sourceDir);
            return false;
        }

        // 获取源目录的父级目录
        String parentDir = dir.getParent();
        // 创建压缩文件的完整路径
        String destZipFileName = parentDir + File.separator + zipFileName;

        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(destZipFileName))) {
            zipDirectory(dir, dir.getName(), zos);
            System.out.println("压缩完成: " + destZipFileName);
            return true;
        } catch (IOException e) {
            e.printStackTrace(); // 输出详细异常信息
            System.out.println("压缩文件夹时出错: " + e.getMessage());
            return false;
        }
    }



    private void zipDirectory(File folder, String parentFolder, ZipOutputStream zos) throws IOException {
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isHidden()) {
                    continue; // 跳过隐藏文件
                }
                if (file.isDirectory()) {
                    zipDirectory(file, parentFolder + File.separator + file.getName(), zos);
                } else {
                    zos.putNextEntry(new ZipEntry(parentFolder + File.separator + file.getName()));
                    try (FileInputStream fis = new FileInputStream(file)) {
                        byte[] buffer = new byte[1024];
                        int len;
                        while ((len = fis.read(buffer)) > 0) {
                            zos.write(buffer, 0, len);
                        }
                    }
                    zos.closeEntry();
                }
            }
        }
    }

    public boolean decompress(String sourceDir, String destDirName) {
        File dir = new File(sourceDir);
        if (!dir.exists() ) {
            System.out.println("源压缩包不存在: " + sourceDir);
            return false;
        }
        // 获取源目录的父级目录
        String parentDir = dir.getParent();
        // 创建压缩文件的完整路径
        String destUnZipFileName = parentDir + File.separator + destDirName;
        File destDir = new File(destUnZipFileName);
        // 如果目标目录不存在，则创建
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(dir))) {
            ZipEntry zipEntry;
            while ((zipEntry = zis.getNextEntry()) != null) {
                File newFile = new File(destUnZipFileName, zipEntry.getName());
                // 如果是目录，则创建
                if (zipEntry.isDirectory()) {
                    newFile.mkdirs();
                } else {
                    // 创建文件的父目录
                    new File(newFile.getParent()).mkdirs();
                    // 写入文件内容
                    try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(newFile))) {
                        byte[] buffer = new byte[1024];
                        int len;
                        while ((len = zis.read(buffer)) > 0) {
                            bos.write(buffer, 0, len);
                        }
                    }
                }
                zis.closeEntry();
            }
            System.out.println("解压完成: " + destDirName);
            return true;
        } catch (IOException e) {
            System.out.println("解压文件时出错: " + e.getMessage());
            return false;
        }
    }


    private File copyFile;
    private long srcArea, destArea, startTime;

    public void copyFile(String src) {
        this.copyFile = new File(src);
        this.srcArea = countDirSize(copyFile.getAbsolutePath());
        this.destArea = 0;
        startTime = System.currentTimeMillis();
    }


    public void copyDirectory(File srcDir, File destDir) {
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
        File[] files = srcDir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    copyDirectory(file, new File(destDir, file.getName()));
                } else {
                    copySingleFile(file, new File(destDir, file.getName()));
                }
            }
        }
    }

    private void copySingleFile(File src, File dest) {
        try (InputStream in = new FileInputStream(src);
             OutputStream out = new FileOutputStream(dest)) {
            IOUtils.copy(in, out);
            destArea += src.length();
            showProgress(src.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showProgress(String fileName) {
        double completionRate = destArea * 1.0 / srcArea;
        long curTime = System.currentTimeMillis() - startTime;
        System.out.printf("%-20s\t 已运行时间: %d 毫秒\t 当前复制完成比: %.2f%%\n", fileName, curTime, 100 * completionRate);
        if (completionRate >= 1) {
            System.out.println("文件夹复制完成");
        }
    }

    private long countDirSize(String path) {
        File file = new File(path);
        long size = 0;
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                size += countDirSize(f.getAbsolutePath());
            }
        } else {
            size += file.length();
        }
        return size;
    }



}
