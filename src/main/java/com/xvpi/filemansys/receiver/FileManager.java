package com.xvpi.filemansys.receiver;

import java.io.*;
import java.nio.file.*;
import java.util.List;

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
    public void copyFile(String sourceFile, String destinationFile) {
        try {
            Path sourcePath = Paths.get(sourceFile);
            Path destinationPath = Paths.get(destinationFile);
            Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File copied to: " + destinationFile);
        } catch (IOException e) {
            e.printStackTrace();
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
}
