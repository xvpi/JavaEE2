package com.xvpi.filemansys.receiver;

import java.io.File;

public abstract class AbstractFileManager implements FileOperations, DirectoryOperations {
    protected String currentDirectory = System.getProperty("user.dir");

    public String getCurrentDirectory() {
        return currentDirectory;
    }

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


}

