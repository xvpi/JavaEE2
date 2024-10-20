package com.xvpi.filemansys.command;

import com.xvpi.filemansys.receiver.FileManager;

import java.io.File;
import java.util.Scanner;

public class SetWorkingDirectoryCommand implements Command {
    private FileManager fileManager;

    public SetWorkingDirectoryCommand(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("当前工作文件夹为: " + fileManager.getCurrentDirectory());
        System.out.println("是否使用默认工作文件夹? (Y/N): ");
        String choice = scanner.nextLine();

        if (choice.equalsIgnoreCase("N")) {
            System.out.println("请输入新的工作文件夹路径: ");
            String newPath = scanner.nextLine();
            File newDirectory = new File(newPath);

            if (newDirectory.exists() && newDirectory.isDirectory()) {
                fileManager.setCurrentDirectory(newDirectory);
                System.out.println("工作文件夹已更改为: " + newPath);
            } else {
                System.out.println("无效的文件夹路径。继续使用默认文件夹。");
            }
        } else {
            System.out.println("继续使用默认工作文件夹。");
        }
    }
}