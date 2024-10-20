package com.xvpi.filemansys.receiver;

import java.io.*;
import java.nio.file.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.util.zip.ZipInputStream;
import java.io.File;

public class FileManager {
    private File currentDirectory;

    public FileManager() {
        // 默认工作文件夹为当前程序运行路径
        this.currentDirectory = new File(System.getProperty("user.dir"));
    }

    public File getCurrentDirectory() {
        return currentDirectory;
    }

    public void setCurrentDirectory(File directory) {
        this.currentDirectory = directory;
    }


    public void setWorkingDirectory(String path) {
        File newDir = new File(path);
        if (newDir.exists() && newDir.isDirectory()) {
            this.currentDirectory = newDir;
            System.out.println("工作文件夹已更改为: " + path);
        } else {
            System.out.println("无效的文件夹路径。");
        }
    }

    public void createFile(String fileName) {
        File newFile = new File(currentDirectory, fileName);
        try {
            if (newFile.createNewFile()) {
                System.out.println("文件已创建: " + newFile.getName());
            } else {
                System.out.println("文件已存在。");
            }
        } catch (Exception e) {
            System.out.println("创建文件时发生错误。");
        }
    }

    public void deleteFile(String fileName) {
        File fileToDelete = new File(currentDirectory, fileName);
        if (fileToDelete.exists() && fileToDelete.isFile()) {
            if (fileToDelete.delete()) {
                System.out.println("文件已删除: " + fileName);
            } else {
                System.out.println("无法删除文件。");
            }
        } else {
            System.out.println("文件不存在。");
        }
    }

    public void listFiles() {
        File[] files = currentDirectory.listFiles();
        if (files != null && files.length > 0) {
            System.out.println("当前文件夹中的文件和文件夹：");
            for (File file : files) {
                System.out.println(file.getName());
            }
        } else {
            System.out.println("当前文件夹为空。");
        }
    }

    public void createDirectory(String dirName) {
        File newDir = new File(currentDirectory, dirName);
        if (newDir.mkdir()) {
            System.out.println("文件夹已创建: " + dirName);
        } else {
            System.out.println("无法创建文件夹，文件夹可能已存在。");
        }
    }

    public void deleteDirectory(String dirName) {
        File dirToDelete = new File(currentDirectory, dirName);
        if (dirToDelete.exists() && dirToDelete.isDirectory()) {
            if (dirToDelete.delete()) {
                System.out.println("文件夹已删除: " + dirName);
            } else {
                System.out.println("无法删除文件夹，文件夹可能不为空。");
            }
        } else {
            System.out.println("文件夹不存在。");
        }
    }
    // 文件复制
    public void copyFile(String sourceFileName, String destinationFileName) {
        File sourceFile = new File(currentDirectory, sourceFileName);
        File destinationFile = new File(currentDirectory, destinationFileName);

        try {
            Files.copy(sourceFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("文件已复制: " + sourceFileName + " 到 " + destinationFileName);
        } catch (IOException e) {
            System.out.println("复制文件时出错: " + e.getMessage());
        }
    }

    // 深度文件夹复制
    public void copyDirectory(File sourceDirectory, File destinationDirectory) {
        if (!sourceDirectory.exists()) {
            System.out.println("源文件夹不存在!");
            return;
        }

        if (!destinationDirectory.exists()) {
            destinationDirectory.mkdirs(); // 创建目标文件夹
        }

        File[] files = sourceDirectory.listFiles();
        if (files != null) {
            for (File file : files) {
                File newFile = new File(destinationDirectory, file.getName());
                if (file.isDirectory()) {
                    copyDirectory(file, newFile);
                } else {
                    try {
                        Files.copy(file.toPath(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException e) {
                        System.out.println("复制文件夹时出错: " + e.getMessage());
                    }
                }
            }
        }
    }

    public void compress(String sourceDir, String zipFileName) {
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFileName))) {
            File dir = new File(sourceDir);
            zipDirectory(dir, dir.getName(), zos);
            System.out.println("压缩完成: " + zipFileName);
        } catch (IOException e) {
            System.out.println("压缩文件夹时出错: " + e.getMessage());
        }
    }

    private void zipDirectory(File folderToZip, String zipEntryName, ZipOutputStream zos) throws IOException {
        for (File file : folderToZip.listFiles()) {
            if (file.isDirectory()) {
                zipDirectory(file, zipEntryName + "/" + file.getName(), zos);
                continue;
            }
            try (FileInputStream fis = new FileInputStream(file)) {
                ZipEntry zipEntry = new ZipEntry(zipEntryName + "/" + file.getName());
                zos.putNextEntry(zipEntry);
                byte[] buffer = new byte[1024];
                int len;
                while ((len = fis.read(buffer)) > 0) {
                    zos.write(buffer, 0, len);
                }
                zos.closeEntry();
            }
        }
    }

    public void decompress(String zipFileName, String destDir) {
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFileName))) {
            ZipEntry zipEntry;
            while ((zipEntry = zis.getNextEntry()) != null) {
                File newFile = new File(destDir, zipEntry.getName());
                if (zipEntry.isDirectory()) {
                    newFile.mkdirs();
                } else {
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
            System.out.println("解压完成: " + zipFileName);
        } catch (IOException e) {
            System.out.println("解压文件夹时出错: " + e.getMessage());
        }
    }
}
