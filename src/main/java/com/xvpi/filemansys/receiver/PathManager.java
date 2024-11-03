package com.xvpi.filemansys.receiver;

import java.io.File;

public class PathManager {
    private FileManager fileManager;

    public PathManager(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    public void changeDirectory(String path) {
        File newDirectory = new File(path);

        // 如果是相对路径，则拼接当前工作目录
        if (!newDirectory.isAbsolute()) {
            String currentDirectory = fileManager.getCurrentDirectory(); // 获取当前工作目录
            newDirectory = new File(currentDirectory, path);
        }

        if (newDirectory.exists() && newDirectory.isDirectory()) {
            fileManager.setCurrentDirectory(newDirectory.getAbsolutePath());
            System.out.println("已切换到路径：" + newDirectory.getAbsolutePath());
        } else {
            System.out.println("路径无效或不是一个目录：" + path);
        }
    }


    public void moveUpOneLevel() {
        File currentDir = new File(fileManager.getCurrentDirectory());
        fileManager.setCurrentDirectory(currentDir.getParent());
        System.out.println("已返回上一层：" + fileManager.getCurrentDirectory());
    }

    public void moveUpTwoLevels() {
        File currentDir = new File(fileManager.getCurrentDirectory()).getParentFile().getParentFile();
        fileManager.setCurrentDirectory(currentDir.getPath());
        System.out.println("已返回上两层：" + fileManager.getCurrentDirectory());
    }

    public void showCurrentPath() {
        System.out.println("当前工作路径：" + fileManager.getCurrentDirectory());
    }

    public String getCurrentDirectory() {
        return fileManager.getCurrentDirectory();
    }
}
